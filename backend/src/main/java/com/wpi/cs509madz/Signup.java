package com.wpi.cs509madz;

public class Signup extends Authenticate implements ISignup {

    public Signup(String username, String password) {

        super(username, password);
    }

    @Override
    public void sendRequest() {

        if (userDatabase.containsKey(username)) {

            System.out.println("Username already taken. Please choose another.");
        }
        else if (!validPassword(password)) {

            System.out.println("Password does not meet the required criteria.");
        }
        else {

            userDatabase.put(username, password);
            System.out.println("Signup successful! You can now log in.");
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
