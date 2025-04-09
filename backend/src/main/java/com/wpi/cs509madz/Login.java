package com.wpi.cs509madz;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.sql.SQLException;

public class Login extends Authenticate implements IAuthenticate {

    //A database of usernames (key) and passwords (value) that is shared amongst
    //all instances of the Authenticate class

    public Login(String username, String password, DatabaseManager user_db) {

        super(username, password, user_db);
    }

    @Override
    public void sendRequest() {

        if (validData()) {

            System.out.println("Login successful! Welcome, " + username);
        }
        else {

            System.out.println("Invalid username or password. Please try again.");
        }
    }
}
