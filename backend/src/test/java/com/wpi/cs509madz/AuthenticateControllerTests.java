package com.wpi.cs509madz;

import com.wpi.cs509madz.controller.AuthenticateController;
import com.wpi.cs509madz.model.User;
import com.wpi.cs509madz.repository.UserRepository;
import com.wpi.cs509madz.service.authenticateService.Authenticate;
import com.wpi.cs509madz.service.authenticateService.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AuthenticateControllerTests {

    @Mock
    private DatabaseManager database_manager_mock;
    private AuthenticateController authenticate_controller;

    @BeforeEach
    public void setUp() {

        //A mock of the database manager ensures only AuthenticateController is tested
        database_manager_mock = mock(DatabaseManager.class);
        authenticate_controller = new AuthenticateController(database_manager_mock);
    }


    @Test
    public void test_signUp_with_valid_credentials_should_return_200() {

        //Initialize the request and mock that registerUser returns 0 when called; the authentication is successful
        Authenticate request = new Authenticate("new_user", "CompSci509!", database_manager_mock);
        when(database_manager_mock.registerUser(anyString(), anyString())).thenReturn(0);

        ResponseEntity<?> response = authenticate_controller.signUp(request);

        //When function is run, a response of HTTP 200 OK should be returned. "Registration successful"
        //should also be printed to the console
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Registration successful"));
    }

    @Test
    public void test_signUp_with_username_already_taken_should_return_401() {

        //Initialize the request and mock that registerUser returns 1 when called; the provided username is taken
        Authenticate request = new Authenticate("existing_user", "CompSci509!", database_manager_mock);
        when(database_manager_mock.registerUser(anyString(), anyString())).thenReturn(1);

        ResponseEntity<?> response = authenticate_controller.signUp(request);

        //When function is run, a response of HTTP 401 UNAUTHORIZED should be returned. "Username already
        //taken" should also be printed to the console
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Username already taken"));
    }

    @Test
    public void test_signUp_with_invalid_password_should_return_401() {

        //Initialize the request and mock that registerUser returns 2 when called; the password is invalid
        Authenticate request = new Authenticate("new_user", "bad", database_manager_mock);
        when(database_manager_mock.registerUser(anyString(), anyString())).thenReturn(2);

        ResponseEntity<?> response = authenticate_controller.signUp(request);

        //When function is run, a response of HTTP 401 UNAUTHORIZED should be returned. "Password does not
        //meet requirements" should also be printed to the console
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Password does not meet requirements"));
    }

    @Test
    public void test_signUp_with_database_error_should_return_500() {

        //Initialize the request and mock that registerUser returns -1 when called; a database error occurred
        Authenticate request = new Authenticate("new_user", "CompSci509!", database_manager_mock);
        when(database_manager_mock.registerUser(anyString(), anyString())).thenReturn(-1);

        ResponseEntity<?> response = authenticate_controller.signUp(request);

        //When function is run, a response of HTTP 500 INTERNAL SERVER ERROR should be returned. "Database
        //error" should also be printed to the console
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Database error"));
    }


    @Test
    public void test_signIn_with_valid_credentials_should_return_200() throws Exception {

        //Initialize the request and mock that searchUser returns true when called; the user was found when searched
        Authenticate request = new Authenticate("existing_user", "CompSci509!", database_manager_mock);
        when(database_manager_mock.searchUser("existing_user", "CompSci509!")).thenReturn(true);

        //Create a mock user; only the id is relevant in this test
        User user_mock = new User();
        user_mock.setId(100200300);

        //Mock the user repository so that when returnUserByUsername is called, user_mock is returned
        //Also, when the database manager mock calls getRepository, user_repository_mock is returned
        UserRepository user_repository_mock = mock(UserRepository.class);
        when(user_repository_mock.returnUserByUsername("existing_user")).thenReturn(List.of(user_mock));
        when(database_manager_mock.getRepository()).thenReturn(user_repository_mock);

        ResponseEntity<?> response = authenticate_controller.signIn(request);

        //When function is run, a response of HTTP 200 OK should be returned. "Login successful" as well
        //as the ID of the user mock should also be printed to the console
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Login successful"));
        assertTrue(response.getBody().toString().contains("100200300"));
    }

    @Test
    public void test_signIn_with_invalid_credentials_should_return_401() throws Exception {

        //Initialize the request and mock that searchUser returns false when called; the user was not found
        Authenticate request = new Authenticate("non_existing_user", "CompSci509!", database_manager_mock);
        when(database_manager_mock.searchUser("non_existing_user", "CompSci509!")).thenReturn(false);

        ResponseEntity<?> response = authenticate_controller.signIn(request);

        //When function is run, a response of HTTP 401 UNAUTHORIZED should be returned. "Invalid username or
        //"password" should also be printed to the console
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Invalid username or password"));
    }

    @Test
    public void test_signIn_with_exception_should_return_500() throws Exception {

        //Initialize the request and mock that searchUser throws an Exception when run
        Authenticate request = new Authenticate("user", "password", database_manager_mock);
        when(database_manager_mock.searchUser("user", "password")).thenThrow(new RuntimeException("Database error occurred"));

        ResponseEntity<?> response = authenticate_controller.signIn(request);

        //When function is run, a response of HTTP 500 INTERNAL SERVER ERROR should be returned. "Database
        //error occurred" should also be printed to the console
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Database error occurred"));
    }


}
