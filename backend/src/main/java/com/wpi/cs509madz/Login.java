package com.wpi.cs509madz;

public class Login extends Authenticate implements ILogin {

    public Login(String username, String password) {

        super(username, password);
    }

    @Override
    public void sendRequest() {

        if (validData()) {  // Uses the method from Authenticate to check credentials

            System.out.println("Login successful! Welcome, " + username);
        }
        else {

            System.out.println("Invalid username or password. Please try again.");
        }
    }
}
