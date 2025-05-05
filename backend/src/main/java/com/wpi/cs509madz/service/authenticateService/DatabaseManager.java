package com.wpi.cs509madz.service.authenticateService;

//This imports necessary SQL classes, specifically the Connection class, that allows for Java
//to connect with MySQL databases
import com.wpi.cs509madz.repository.UserRepository;
import com.wpi.cs509madz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Repository
public class DatabaseManager {

    //How many hashings are done
    private static final int ITERATIONS = 65536;

    //How long the resulting hash will be
    private static final int KEY_LENGTH = 256;

    //COMMENT CODE
    private final UserRepository user_repository;

    //This is the constructor for the DatabaseManager class. It throws an exception in the event
    //that a connection cannot be established or the incorrect username or password were provided
    @Autowired
    public DatabaseManager(UserRepository user_repository) {

        //The provided url, username, and password are used on a class called DriverManager, which
        //is also provided by the imported java.sql package. The static getConnection() function
        //returns a connection object that is stored in the db_connection attribute already
        //initialized
        this.user_repository = user_repository;
    }


    public UserRepository getRepository() {

        return user_repository;
    }


    public int registerUser(String username, String password) {

        if (!validPassword(password)) {

            //Invalid password, return 2
            return 2;
        }

        int result = user_repository.doesUserExist(username);

        if (result == 1) {

            //Username already taken, return 1
            return 1;
        }

        //Generate a salt
        byte[] salt = generateSalt();

        //Hash the password with the salt
        String hashed_password;

        try {

            hashed_password = hashPassword(password, salt);
        }
        catch (Exception e) {

            System.out.println(e.getMessage());
            return -1;
        }

        User user = new User();
        user.setId(createID(user_repository));
        user.setUsername(username);
        user.setPassword(hashed_password);
        user.setSalt(salt);

        user_repository.save(user);

        return 0;
    }


    public boolean searchUser(String username, String password) throws Exception {

        // You’d compare hashed passwords here in a real implementation
        List<User> user = user_repository.getUserViaUsername(username);

        if (!(user.isEmpty())) {

            return verifyPassword(password, user.get(0).getPassword(), user.get(0).getSalt());
        }
        else {

            return false;
        }
    }

    static public int createID(UserRepository user_repository) {

        Random random = new Random();

        int id;

        do {

            id = 100_000_000 + random.nextInt(900_000_000); // Ensures a 9-digit number
        }
        while (user_repository.doesIdExist(id));

        return id;
    }


    // Generates a random salt
    public byte[] generateSalt() {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


    // Hashes a password using the given salt
    public String hashPassword(String password, byte[] salt) throws Exception {

        //PBEKeySpec = Password-Based Encryption Key Specification
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

        //Process the PBEKeySpec into an actual secure hash
        //PBKDF2 = Password-Based Key Derivation Function 2
        //HmacSHA256 = the hashing engine it uses
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        //generateSecret generates the hash, and then getEncoded converts it into a byte array
        byte[] hash = factory.generateSecret(spec).getEncoded();

        //encodeToString converts the byte array into the encrypted string!
        return Base64.getEncoder().encodeToString(hash);
    }


    public boolean verifyPassword(String enteredPassword, String storedHash, byte[] storedSalt) throws Exception {

        // Recreate the hash of the entered password using the same salt
        String enteredHash = hashPassword(enteredPassword, storedSalt);

        // Compare the newly created hash with the stored hash
        return enteredHash.equals(storedHash);
    }


    public boolean validPassword(String password) {

        if (password.length() < 8) {

            return false; // Password too short
        }
        if (!password.matches(".*[A-Z].*")) {

            return false; // Password must contain at least one uppercase letter
        }
        if (!password.matches(".*[0-9].*")) {

            return false; // Password must contain at least one digit
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {

            return false; // Password must contain at least one special character
        }
        return true; // Password is valid
    }
}
