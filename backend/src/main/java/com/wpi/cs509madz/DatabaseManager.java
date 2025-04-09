package com.wpi.cs509madz;

//This imports necessary SQL classes, specifically the Connection class, that allows for Java
//to connect with MySQL databases
import java.sql.*;

import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class DatabaseManager {

    //How many hashings are done
    private static final int ITERATIONS = 65536;

    //How long the resulting hash will be
    private static final int KEY_LENGTH = 256;

    //The Connection object is initialized as an attribute of this class. It will be used to
    //run MySQL queries
    private final Connection db_connection;

    //This is the constructor for the DatabaseManager class. It throws an exception in the event
    //that a connection cannot be established or the incorrect username or password were provided
    public DatabaseManager(String url, String user, String password) throws SQLException {

        //The provided url, username, and password are used on a class called DriverManager, which
        //is also provided by the imported java.sql package. The static getConnection() function
        //returns a connection object that is swtored in the db_connection attribute already
        //initialized
        this.db_connection = DriverManager.getConnection(url, user, password);
    }


    public int registerUser(String username, String password) throws SQLException {

        try (PreparedStatement check = db_connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {

            check.setString(1, username);
            ResultSet result = check.executeQuery();

            if (result.next() && result.getInt(1) > 0) {

                //Username already taken, return 0
                return 0;
            }
        }

        //Generate a salt
        byte[] salt = generateSalt();

        //Hash the password with the salt
        String hashed_password = null;
        try {

            hashed_password = hashPassword(password, salt);
        }
        catch (Exception e) {

            System.out.println(e.getMessage());
            return -1;
        }

        try (PreparedStatement insert = db_connection.prepareStatement(
                "INSERT INTO users (ID, username, password, salt) VALUES (?, ?, ?, ?)")) {

            insert.setInt(1, DatabaseManager.createID());
            insert.setString(2, username);
            insert.setString(3, hashed_password);
            insert.setBytes(4, salt);

            int rows_affected = insert.executeUpdate();

            if (rows_affected > 0) {

                //User registration is a success, return 1
                return 1;
            }
        }

        //An error occurred, return -1
        return -1;
    }


    public boolean searchUser(String username, String password)  throws SQLException {

        try (PreparedStatement query = db_connection.prepareStatement("SELECT password, salt FROM users WHERE username = ?")) {

            query.setString(1, username);

            ResultSet result = query.executeQuery();

            if (result.next()) {

                String storedHash = result.getString("password");
                byte[] storedSalt = result.getBytes("salt");

                try {

                    // Verify the entered password using the stored hash and salt
                    return verifyPassword(password, storedHash, storedSalt);
                }
                catch (Exception e) {

                    e.printStackTrace();  // Handle the exception properly
                }
            }
        }
        catch (SQLException error) {

            error.printStackTrace();
        }

        return false;
    }


    static public int createID() {

        Random random = new Random();
        return 100_000_000 + random.nextInt(900_000_000); // Ensures a 9-digit number
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
}
