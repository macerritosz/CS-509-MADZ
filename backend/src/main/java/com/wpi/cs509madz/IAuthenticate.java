package com.wpi.cs509madz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public interface IAuthenticate {

    public boolean validData();

    public abstract void sendRequest();
}
