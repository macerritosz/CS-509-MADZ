package com.wpi.cs509madz.service.authenticateService;


public class Authenticate {

    protected String username;
    protected String password;

    protected DatabaseManager user_db;

    public Authenticate(String username, String password, DatabaseManager user_db) {

        this.username = username;
        this.password = password;
        this.user_db = user_db;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setPassword(String password) {

        this.password = password;
    }


}
