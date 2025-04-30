package java.com.wpi.cs509madz;

import com.wpi.cs509madz.controller.AuthenticateController;
import com.wpi.cs509madz.service.authenticateService.Authenticate;
import com.wpi.cs509madz.service.authenticateService.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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


    //DON'T NEED TO MAKE A MOCK OF AUTHENTICATE, RIGHT???
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
}
