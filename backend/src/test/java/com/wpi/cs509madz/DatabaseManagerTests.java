package com.wpi.cs509madz;

import com.wpi.cs509madz.model.User;
import com.wpi.cs509madz.service.authenticateService.DatabaseManager;
import com.wpi.cs509madz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DatabaseManagerTests {

    private UserRepository user_repository_mock;
    private DatabaseManager database_manager;

    @BeforeEach
    public void setUp() {

        //A mock of the user repository ensures only DatabaseManager is tested
        user_repository_mock = mock(UserRepository.class);
        database_manager = new DatabaseManager(user_repository_mock);
    }


    @Test
    public void test_registerUser_with_valid_input_should_return_0() {

        String username = "new_user";
        String password = "WorkingPassword#1";

        //findUserByUsername returns 0 in this case, as username is unique
        when(user_repository_mock.findUserByUsername(username)).thenReturn(0);

        //doesIdExist returns false in this case
        when(user_repository_mock.doesIdExist(anyInt())).thenReturn(false);

        //When function is run, 0 should be returned; user was registered with no issues
        int result = database_manager.registerUser(username, password);
        assertEquals(0, result);
    }

    @Test
    public void test_registerUser_when_username_taken_should_return_1() {

        String username = "existing_user";
        String password = "WorkingPassword#2";

        //findUserByUsername returns 1 in this case, username is not unique
        when(user_repository_mock.findUserByUsername(username)).thenReturn(1);

        //When function is run, 1 should be returned; user wasn't registered
        int result = database_manager.registerUser(username, password);
        assertEquals(1, result);
    }

    @Test
    public void test_registerUser_with_invalid_password_should_return_2() {

        String username = "invalid_user";
        String password = "bad";

        //When function is run, 2 should be returned; user wasn't registered
        int result = database_manager.registerUser(username, password);
        assertEquals(2, result);
    }

    @Test
    public void test_RegisterUser_with_hashPassword_throwing_exception_should_return_negative_1() throws Exception {

        //A spy of database_manager is made in this instance, to function mostly like database_manager
        //except when triggering the error with hashPassword
        DatabaseManager database_manager = spy(new DatabaseManager(user_repository_mock));

        String username = "new_user";
        String password = "GoodPassword#1";

        //findUserByUsername returns 0 in this case, as username is unique
        when(user_repository_mock.findUserByUsername(username)).thenReturn(0);

        //doesIdExist returns false in this case
        when(user_repository_mock.doesIdExist(anyInt())).thenReturn(false);

        //Force an exception when database_manager calls hashPassword
        doThrow(new Exception("Mocked exception")).when(database_manager).hashPassword(anyString(), any());

        //When function is run, -1 should be returned; an error occurred
        int result = database_manager.registerUser(username, password);
        assertEquals(-1, result);
    }


    @Test
    public void test_searchUser_with_existing_user_should_return_true() throws Exception {

        String username = "existing_user";
        String password = "CompSci509!";

        byte[] salt = database_manager.generateSalt();
        String hashed_password = database_manager.hashPassword(password, salt);

        //When making user mock, only username, password and salt are needed for testing
        User user_mock = new User();
        user_mock.setUsername(username);
        user_mock.setPassword(hashed_password);
        user_mock.setSalt(salt);

        //findUserByUsernameUser returns the mock user in this case, as it was provided with the user's username
        when(user_repository_mock.returnUserByUsername(username)).thenReturn(List.of(user_mock));

        //When function is run, true should be returned; the user was found
        boolean result = database_manager.searchUser(username, password);
        assertTrue(result);
    }

    @Test
    public void test_searchUser_with_nonexistent_user_should_return_false() throws Exception {

        String username = "nonexistent_user";
        String password = "CompSci509!";

        //findUserByUsernameUser returns an empty list in this case, as it was provided with nonexistent credentials
        when(user_repository_mock.returnUserByUsername(username)).thenReturn(List.of());

        //When function is run, false should be returned; the user was not found
        boolean result = database_manager.searchUser(username, password);
        assertFalse(result);
    }

    @Test
    public void test_searchUser_with_wrong_password_should_return_false() throws Exception {

        String username = "existing_user";
        String correct_pass = "CorrectPassword#1";
        String wrong_pass = "WrongPassword#2";

        byte[] salt = database_manager.generateSalt();
        String hashed_correct_pass = database_manager.hashPassword(correct_pass, salt);

        //When making user mock, correct password is provided
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(hashed_correct_pass);
        mockUser.setSalt(salt);

        //findUserByUsernameUser returns the mock user in this case, as it was provided with the user's username
        when(user_repository_mock.returnUserByUsername(username)).thenReturn(List.of(mockUser));

        //When function is run, false should be returned; the user was found, but the password provided does not match
        boolean result = database_manager.searchUser(username, wrong_pass);
        assertFalse(result);
    }


    @Test
    public void test_createID_generates_unique_9_digit_id() {

        //doesIdExist returns true on first two calls, then false on third call
        when(user_repository_mock.doesIdExist(anyInt()))
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        //createID is then called with the mock of user_repository implemented
        int user_id = DatabaseManager.createID(user_repository_mock);

        //Extra check to ensure that the id is 9 digits
        assertTrue(user_id >= 100_000_000 && user_id <= 999_999_999);

        //When function is run, in this particular instance, doesIdExist should be called 3 times total
        verify(user_repository_mock, atLeast(3)).doesIdExist(anyInt());
    }


    @Test
    public void test_generateSalt_should_return_different_salts() {

        byte[] salt_1 = database_manager.generateSalt();
        byte[] salt_2 = database_manager.generateSalt();

        //The two salts that were just generated should be compared and determined to not be equal
        assertFalse(Arrays.equals(salt_1, salt_2));
    }


    @Test
    public void test_hashPassword_with_valid_input_should_return_valid_hash() throws Exception {

        String password = "CompSci509!";

        //generateSalt() should function properly if the test above passes
        byte[] salt = database_manager.generateSalt();

        String hashed_password = database_manager.hashPassword(password, salt);

        //hashed_password should not be null, and it should also
        assertNotNull(hashed_password, "Hashed password should not be null");

        //hashed_password should also have a length of about 44 characters, equal to 256 bits
        int expected_length = 44;
        assertEquals(expected_length, hashed_password.length());
    }

    //THIS PASSES, DO WE WANT IT TO PASS???
    //OR DO WE WANT SAME PASSWORDS TO STILL HAVE DIFFERENT HASHES
    //OH NVM IT PASSES CAUSE SALT IS THE SAME
    @Test
    public void test_hashPassword_with_same_input_should_return_same_hashes() throws Exception {

        String password = "SamePassword@2";

        //generateSalt() should function properly if the test above passes
        byte[] salt = database_manager.generateSalt();

        String hashed_password_1 = database_manager.hashPassword(password, salt);
        String hashed_password_2 = database_manager.hashPassword(password, salt);

        assertEquals(hashed_password_1, hashed_password_2);
    }

    @Test
    public void test_hashPassword_with_different_password_should_return_different_hashes() throws Exception {

        String password_1 = "UniquePassword$1";
        String password_2 = "UniquePassword$2";

        //generateSalt() should function properly if the test above passes
        byte[] salt = database_manager.generateSalt();

        String hashed_password_1 = database_manager.hashPassword(password_1, salt);
        String hashed_password_2 = database_manager.hashPassword(password_2, salt);

        //Even though the salt is the same, the passwords are different, so the hashings should be different
        assertNotEquals(hashed_password_1, hashed_password_2);
    }

    @Test
    public void test_hashPassword_with_same_password_but_different_salts_should_return_different_hashes() throws Exception {

        String password = "SamePassword9!";

        //generateSalt() should function properly if the test above passes
        byte[] salt_1 = database_manager.generateSalt();
        byte[] salt_2 = database_manager.generateSalt();

        String hashed_password_1 = database_manager.hashPassword(password, salt_1);
        String hashed_password_2 = database_manager.hashPassword(password, salt_2);

        //Even though the password is the same, the salts are different, so the hashings should be different
        assertNotEquals(hashed_password_1, hashed_password_2);
    }


    @Test
    public void test_verifyPassword_with_correct_password_should_return_true() throws Exception {

        String password = "CompSci509!";

        //generateSalt() should function properly if the test above passes
        byte[] salt = database_manager.generateSalt();

        //Hash the password and then use it to verify the same password
        String stored_hash = database_manager.hashPassword(password, salt);
        boolean result = database_manager.verifyPassword(password, stored_hash, salt);

        //When function is run, the passwords are the same, so true should be returned
        assertTrue(result);
    }

    @Test
    public void test_verifyPassword_with_incorrect_password_should_return_false() throws Exception {

        String correct_pass = "CompSci509!";
        String incorrect_pass = "WrongPassword123!";

        //generateSalt() should function properly if the test above passes
        byte[] salt = database_manager.generateSalt();

        //Hash the correct password and then use it to not verify the different password
        String correct_hash = database_manager.hashPassword(correct_pass, salt);
        boolean result = database_manager.verifyPassword(incorrect_pass, correct_hash, salt);

        //When function is run, the passwords are not the same, so false should be returned
        assertFalse(result);
    }


    @Test
    public void test_validPassword_with_too_short_password_should_return_false() {

        String password = "2Short!";
        assertFalse(database_manager.validPassword(password));
    }

    @Test
    public void test_validPassword_with_no_uppercase_letter_should_return_false() {

        String password = "no_lowercase1!";
        assertFalse(database_manager.validPassword(password));
    }

    @Test
    public void test_validPassword_with_no_digit_should_return_false() {

        String password = "No_Digits!";
        assertFalse(database_manager.validPassword(password));
    }

    @Test
    public void test_validPassword_with_no_special_character_should_return_false() {

        String password = "No_Character1";
        assertFalse(database_manager.validPassword(password));
    }

    @Test
    public void test_validPassword_with_valid_password_should_return_true() {

        String password = "Valid_Pass#3";
        assertTrue(database_manager.validPassword(password));
    }
}
