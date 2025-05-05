package com.wpi.cs509madz.repository;

import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.service.bookingService.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SouthwestsRepository {

    private static JdbcTemplate jdbcTemplate;
    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        SouthwestsRepository.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Maps the entire database to a list
     * @return a List<Booking> with all the flight information from the Deltas database
     */
    public List<Booking> findAll() {
        String sql = "select * from southwests";
      
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

        List<Flight> flights = jdbcTemplate.query(sql, rowMapper);
        List<Booking> bookings = new ArrayList<>();

        for (Flight flight : flights) {
            bookings.add(new Booking(flight.getFlightNumber(),
                    flight.getDepartDateTime(),
                    flight.getDepartAirport(),
                    flight.getArriveDateTime(),
                    flight.getArriveAirport()));
        }

        return bookings;
    }

    /**
     * Finds a flight based off the SQL given ID
     * @return a List<Flight> with a flight at the given ID
     * Note: even though this will only return 1 flight, using a List makes it easier to parse in the rest of our code
     */
    public List<Flight> getFlightByID(int id) {
        String sql = "select * from deltas where id = ?";

        RowMapper<Flight> rowMapper = new RowMapper<Flight>() {
            @Override
            public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {

                Flight flight = new Flight();
                flight.setId(rs.getInt("id"));
                flight.setArriveAirport(rs.getString("ArriveAirport"));
                flight.setDepartAirport(rs.getString("DepartAirport"));
                flight.setArriveDateTime(rs.getString("ArriveDateTime"));
                flight.setDepartDateTime(rs.getString("DepartDateTime"));
                flight.setFlightNumber(rs.getString("FlightNumber"));

                return flight;
            }
        };

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, String.valueOf(id));
                    }
                },
                rowMapper
        );
    }
}
