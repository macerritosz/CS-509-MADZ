package com.wpi.cs509madz;

import com.wpi.cs509madz.model.User;
import com.wpi.cs509madz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserRepositoryTests {

    @Mock
    private JdbcTemplate jdbc_template_mock;
    private UserRepository user_repository_mock;

    @BeforeEach
    public void setUp() {

        jdbc_template_mock = mock(JdbcTemplate.class);
        user_repository_mock = new UserRepository();
        user_repository_mock.setJdbcTemplate(jdbc_template_mock);
    }


    @Test
    public void test_save_with_user_should_insert_user_into_database() {

        User user_mock = new User();
        user_mock.setId(123456789);
        user_mock.setUsername("test_user");
        user_mock.setPassword("hashed_pass");
        user_mock.setSalt(new byte[]{1, 2});

        user_repository_mock.save(user_mock);

        //When function is run, the jdbc template mock object should make the INSERT command below
        verify(jdbc_template_mock).update(
                eq("INSERT INTO user (id, username, password, salt) VALUES (?, ?, ?,?)"),
                eq(user_mock.getId()),
                eq(user_mock.getUsername()),
                eq(user_mock.getPassword()),
                eq(user_mock.getSalt())
        );
    }


    @Test
    public void test_doesUserExist_with_new_user_should_return_0() {

        String username = "new_user";

        // Mock an empty list to simulate no user found
        when(jdbc_template_mock.query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        )).thenReturn(Collections.emptyList());

        int result = user_repository_mock.doesUserExist(username);

        //Result should be 0, because the user does not exist in the database, meaning the new user can be registered
        assertEquals(0, result);
        verify(jdbc_template_mock).query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        );
    }

    @Test
    public void test_doesUserExist_with_existing_user_should_return_1() {

        String username = "existing_user";
        User user_mock = new User();
        user_mock.setUsername(username);

        List<User> users_mock = new ArrayList<>();
        users_mock.add(user_mock);

        //Mock the JdbcTemplate behavior
        when(jdbc_template_mock.query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        )).thenReturn(users_mock);

        int result = user_repository_mock.doesUserExist(username);

        //Result should be 1, because the username provided already exists in the database
        assertEquals(1, result);
        verify(jdbc_template_mock).query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        );
    }


    @Test
    public void test_getUserViaUsername_with_nonexistent_user_should_return_empty_list() {

        String username = "nonexistent_user";

        when(jdbc_template_mock.query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        )).thenReturn(Collections.emptyList());

        List<User> result = user_repository_mock.getUserViaUsername(username);

        assertTrue(result.isEmpty());
        verify(jdbc_template_mock).query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        );
    }

    @Test
    public void test_getUserViaUsername_with_existing_user_should_return_list_with_user() {

        String username = "existing_user";

        User user_mock = new User();
        user_mock.setId(123456789);
        user_mock.setUsername(username);
        user_mock.setPassword("hashed_pass");
        user_mock.setSalt(new byte[]{1, 2});

        List<User> users_mock = List.of(user_mock);

        when(jdbc_template_mock.query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        )).thenReturn(users_mock);

        List<User> result = user_repository_mock.getUserViaUsername(username);

        assertEquals(1, result.size());
        assertEquals(username, result.get(0).getUsername());
        verify(jdbc_template_mock).query(
                eq("select * from user where username = ?"),
                any(PreparedStatementSetter.class),
                any(RowMapper.class)
        );
    }


    @Test
    public void test_doesIdExist_with_non_existing_id_should_return_false() {

        int id = 987654321;

        //Returns 0 because no id found
        when(jdbc_template_mock.queryForObject(
                eq("SELECT COUNT(*) from user where id = ?"),
                eq(Integer.class),
                eq(id)
        )).thenReturn(0);

        boolean result = user_repository_mock.doesIdExist(id);

        assertFalse(result);
        verify(jdbc_template_mock).queryForObject(
                eq("SELECT COUNT(*) from user where id = ?"),
                eq(Integer.class),
                eq(id)
        );
    }

    @Test
    public void test_doesIdExist_with_existing_id_should_return_true() {

        int id = 123456789;

        //Returns 1 because id found
        when(jdbc_template_mock.queryForObject(
                eq("SELECT COUNT(*) from user where id = ?"),
                eq(Integer.class),
                eq(id)
        )).thenReturn(1);

        boolean result = user_repository_mock.doesIdExist(id);

        assertTrue(result);
        verify(jdbc_template_mock).queryForObject(
                eq("SELECT COUNT(*) from user where id = ?"),
                eq(Integer.class),
                eq(id)
        );
    }

    @Test
    public void test_doesIdExist_with_null_result_should_return_false() {

        int id = 135792468;

        //Returns null because IDK WHY IT RETURNS NULL ACTUALLY HMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMm
        when(jdbc_template_mock.queryForObject(
                eq("SELECT COUNT(*) from user where id = ?"),
                eq(Integer.class),
                eq(id)
        )).thenReturn(null);

        boolean result = user_repository_mock.doesIdExist(id);

        assertFalse(result);
        verify(jdbc_template_mock).queryForObject(
                eq("SELECT COUNT(*) from user where id = ?"),
                eq(Integer.class),
                eq(id)
        );
    }


    @Test
    public void test_doesUserExist_should_call_setValues_and_mapRow() throws SQLException {

        String username = "some_user";

        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.getInt("id")).thenReturn(111);
        when(rsMock.getString("username")).thenReturn(username);
        when(rsMock.getString("password")).thenReturn("pass123");
        when(rsMock.getBytes("salt")).thenReturn(new byte[]{9, 8});

        User expectedUser = new User();
        expectedUser.setId(111);
        expectedUser.setUsername(username);
        expectedUser.setPassword("pass123");
        expectedUser.setSalt(new byte[]{9, 8});

        ArgumentCaptor<PreparedStatementSetter> setterCaptor = ArgumentCaptor.forClass(PreparedStatementSetter.class);
        ArgumentCaptor<RowMapper> rowMapperCaptor = ArgumentCaptor.forClass(RowMapper.class);

        when(jdbc_template_mock.query(
                eq("select * from user where username = ?"),
                setterCaptor.capture(),
                rowMapperCaptor.capture()
        )).thenReturn(List.of(expectedUser));

        int result = user_repository_mock.doesUserExist(username);

        assertEquals(1, result);

        PreparedStatement psMock = mock(PreparedStatement.class);
        setterCaptor.getValue().setValues(psMock);
        verify(psMock).setString(1, username);

        User mappedUser = (User) rowMapperCaptor.getValue().mapRow(rsMock, 1);
        assertEquals(expectedUser.getId(), mappedUser.getId());
        assertEquals(expectedUser.getUsername(), mappedUser.getUsername());
        assertEquals(expectedUser.getPassword(), mappedUser.getPassword());
        assertArrayEquals(expectedUser.getSalt(), mappedUser.getSalt());
    }

    @Test
    public void test_getUserViaUsername_should_call_setValues_and_mapRow() throws SQLException {

        String username = "test_user";

        User expectedUser = new User();
        expectedUser.setId(42);
        expectedUser.setUsername(username);
        expectedUser.setPassword("hashed_pw");
        expectedUser.setSalt(new byte[]{1, 2, 3});

        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.getInt("id")).thenReturn(42);
        when(rsMock.getString("username")).thenReturn(username);
        when(rsMock.getString("password")).thenReturn("hashed_pw");
        when(rsMock.getBytes("salt")).thenReturn(new byte[]{1, 2, 3});

        ArgumentCaptor<PreparedStatementSetter> setterCaptor = ArgumentCaptor.forClass(PreparedStatementSetter.class);
        ArgumentCaptor<RowMapper> rowMapperCaptor = ArgumentCaptor.forClass(RowMapper.class);

        when(jdbc_template_mock.query(
                eq("select * from user where username = ?"),
                setterCaptor.capture(),
                rowMapperCaptor.capture()
        )).thenReturn(List.of(expectedUser));

        List<User> result = user_repository_mock.getUserViaUsername(username);

        assertEquals(1, result.size());
        assertEquals(username, result.get(0).getUsername());

        PreparedStatement psMock = mock(PreparedStatement.class);
        setterCaptor.getValue().setValues(psMock);
        verify(psMock).setString(1, username);

        User mappedUser = (User) rowMapperCaptor.getValue().mapRow(rsMock, 0);
        assertEquals(expectedUser.getId(), mappedUser.getId());
        assertEquals(expectedUser.getUsername(), mappedUser.getUsername());
        assertEquals(expectedUser.getPassword(), mappedUser.getPassword());
        assertArrayEquals(expectedUser.getSalt(), mappedUser.getSalt());
    }

}
