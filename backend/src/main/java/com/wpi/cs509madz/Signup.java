package com.wpi.cs509madz;

import java.sql.SQLException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Signup extends Authenticate implements IAuthenticate {

    //How many hashings are done
    private static final int ITERATIONS = 65536;

    //How long the resulting hash will be
    private static final int KEY_LENGTH = 256;

    public Signup(String username, String password, DatabaseManager user_db) {

        super(username, password, user_db);
    }

    @Override
    public void sendRequest() {

        try {

            if (!validPassword(password)) {

                System.out.println("Password does not meet the required criteria.");
                return;
            }

            int register_result = user_db.registerUser(username, password);

            if (register_result == 0) {

                System.out.println("Username already taken. Please choose another.");
            }
            else if (register_result == -1) {

                System.out.println("An error has occurred. Please try again.");
            }
            else {

                System.out.println("Signup successful! You can now log in.");
            }
        }
        catch (SQLException error) {

            System.out.println("An error has occurred. Please try again.");
        }
    }

    private boolean validPassword(String password) {

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
