package com.wpi.cs509madz.service.authenticateService;

import com.wpi.cs509madz.service.utils.DatabaseManager;

public class Logout extends Authenticate implements IAuthenticate {

    public Logout(String username, DatabaseManager user_db) {

        super(username, "", user_db); //Password isn't needed for logout
    }

    @Override
    public void sendRequest() {

        System.out.println(username + " has logged out.");
    }
}
