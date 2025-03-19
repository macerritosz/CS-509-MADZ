package com.wpi.cs509madz;

import java.sql.SQLException;

public class Signup extends Authenticate implements IAuthenticate {

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
            } else if (register_result == -1) {

                System.out.println("An error has occurred. Please try again.");
            } else {

                System.out.println("Signup successful! You can now log in.");
            }
        }
        catch (SQLException error) {

            System.out.println("An error has occurred. Please try again.");
        }
    }

    // Method to validate the password
    private boolean validPassword(String password) {

        // Password validation logic, e.g., length, special characters, etc.
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
