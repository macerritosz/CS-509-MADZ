package com.wpi.cs509madz.service.authenticateService;

import java.sql.SQLException;

import com.wpi.cs509madz.service.utils.DatabaseManager;

public abstract class Authenticate implements IAuthenticate {

    protected String username;
    protected String password;

    protected DatabaseManager user_db;

    public Authenticate(String username, String password, DatabaseManager user_db) {

        this.username = username;
        this.password = password;
        this.user_db = user_db;
    }

    @Override
    public boolean validData() {

        try {

            return user_db.searchUser(username, password);
        }
        catch (SQLException error) {

            error.printStackTrace();
            return false;
        }
    }

    @Override
    public abstract void sendRequest();

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }
}
