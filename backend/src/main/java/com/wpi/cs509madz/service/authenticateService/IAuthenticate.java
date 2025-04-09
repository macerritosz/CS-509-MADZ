package com.wpi.cs509madz.service.authenticateService;

public interface IAuthenticate {

    public boolean validData();

    public abstract void sendRequest();
}
