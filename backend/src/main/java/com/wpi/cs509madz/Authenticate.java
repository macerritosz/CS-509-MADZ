package com.wpi.cs509madz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public abstract class Authenticate implements IAuthenticate {

    //A database of usernames (key) and passwords (value) that is shared amongst
    //all instances of the Authenticate class
    protected static Map<String, String> userDatabase = new HashMap<>();

    protected String username;
    protected String password;

    public Authenticate(String username, String password) {

        this.username = username;
        this.password = password;
    }

    @Override
    public boolean validData() {

        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }

    @Override
    public abstract void sendRequest();
}
