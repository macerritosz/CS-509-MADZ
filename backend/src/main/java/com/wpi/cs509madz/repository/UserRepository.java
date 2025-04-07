package com.wpi.cs509madz.repository;

import com.wpi.cs509madz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {


    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        //pass the query string into the repository execute the user
        String sql = "INSERT INTO users (username, password) VALUES (?,?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    public int findUserByUsername(String username) {
        String sql = "select * from users where username = ?";

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));

                return u;
            }
        };
        List<User> user =  jdbcTemplate.query(sql, rowMapper);

        return user.size();
    }

    public List<User> authenticateUser(String username, String password) {
        String sql = "select * from users where username = ? and password = ?";

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));

                return u;
            }
        };

        List<User> user =  jdbcTemplate.query(sql, rowMapper);

        return user;
    }

}
