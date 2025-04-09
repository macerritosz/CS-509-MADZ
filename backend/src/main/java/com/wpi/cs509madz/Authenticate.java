package com.wpi.cs509madz;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
