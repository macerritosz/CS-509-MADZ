package com.wpi.cs509madz.repository;

import com.wpi.cs509madz.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SouthwestsRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Flight> findAll() {
        String sql = "select * from southwests";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Flight.class));
    }
}
