package com.wpi.cs509madz.repository;

import com.wpi.cs509madz.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeltasRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DeltasRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Flight> findAll() {
        String sql = "select * from deltas";

        RowMapper<Flight> rowMapper = (rs, rowNum) -> {

            Flight flight = new Flight();
            flight.setId(rs.getInt("id"));
            flight.setArriveAirport(rs.getString("ArriveAirport"));
            flight.setDepartAirport(rs.getString("DepartAirport"));
            flight.setArriveDateTime(rs.getString("ArriveDateTime"));
            flight.setDepartDateTime(rs.getString("DepartDateTime"));
            flight.setFlightNumber(rs.getString("FlightNumber"));

            return flight;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }
}