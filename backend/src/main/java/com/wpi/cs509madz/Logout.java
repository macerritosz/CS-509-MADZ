package com.wpi.cs509madz;

public class Logout extends Authenticate implements ILogout {

    public Logout(String username) {

        super(username, ""); // Password isn't needed for logout
    }

    @Override
    public void sendRequest() {
            
        System.out.println(username + " has logged out.");
    }
}
