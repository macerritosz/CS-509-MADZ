package com.wpi.cs509madz.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import com.wpi.cs509madz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;


    public JdbcTemplate getJdbcTemplate() {

        return jdbcTemplate;
    }


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(User user) {

        //pass the query string into the repository execute the user
        String sql = "INSERT INTO user (id, username, password, salt) VALUES (?, ?, ?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword(), user.getSalt());
    }



    public int findUserByUsername(String username) {

        String sql = "select * from user where username = ?";

        RowMapper<User> rowMapper = new RowMapper<>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                User u = new User();
                u.setId(rs.getInt("ID"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setSalt(rs.getBytes("salt"));

                return u;
            }
        };

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, username);
                    }
                },
                rowMapper
        ).size();
    }


    public List<User> returnUserByUsername(String username) {

        String sql = "select * from user where username = ?";

        RowMapper<User> rowMapper = new RowMapper<>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                User u = new User();
                u.setId(rs.getInt("ID"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setSalt(rs.getBytes("salt"));

                return u;
            }
        };

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, username);
                    }
                },
                rowMapper
        );
    }


    public boolean doesIdExist(int id) {

        String sql = "SELECT COUNT(*) from user where id = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;

    }

    public List<User> authenticateUser(String username, String password) {

        String sql = "select * from user where username = ? and password = ?";

        RowMapper<User> rowMapper = new RowMapper<>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                User u = new User();
                u.setId(rs.getInt("ID"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setSalt(rs.getBytes("salt"));

                return u;
            }
        };

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, username);
                        ps.setString(2, password);
                    }
                },
                rowMapper
        );
    }
}
