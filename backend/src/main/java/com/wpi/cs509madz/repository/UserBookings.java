package com.wpi.cs509madz.repository;

import com.wpi.cs509madz.dto.FlightBookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserBookings {
    private static JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        UserBookings.jdbcTemplate = jdbcTemplate;
    }

    public void save(FlightBookingDto booking) {

        //pass the query string into the repository execute the user
        String sql = "INSERT INTO bookings (DepartDateTime, ArriveDateTime, DepartAirport, ArriveAirport, FlightNumber, UserID, Ref1, Ref2, Airline) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                booking.getDepartDateTime(),
                booking.getArriveDateTime(),
                booking.getDepartAirport(),
                booking.getArriveAirport(),
                booking.getFlightNumber(),
                booking.getUserID(),
                booking.getReferenceIDs().get(0),
                booking.getReferenceIDs().get(1),
                booking.getAirline());
    }

    /**
     * Finds all the flights booked by a specific user
     * @param id an int that represents the user
     * @return a List<FlightBookingDto> with all the flights the user has booked
     */
    public List<FlightBookingDto> getAllFlightsByUserID(int id) {
        String sql = "select * from bookings where UserID = ?";

        RowMapper<FlightBookingDto> rowMapper = (rs, rowNum) -> {

            FlightBookingDto flightBooking = new FlightBookingDto();
            flightBooking.setTableId(rs.getInt("Id"));
            flightBooking.setArriveAirport(rs.getString("ArriveAirport"));
            flightBooking.setDepartAirport(rs.getString("DepartAirport"));
            flightBooking.setArriveDateTime(rs.getString("ArriveDateTime"));
            flightBooking.setDepartDateTime(rs.getString("DepartDateTime"));
            flightBooking.setFlightNumber(rs.getString("FlightNumber"));

            for (int i = 1; i < 5; i++) {
                flightBooking.getReferenceIDs().set(i, rs.getInt("Ref" + i));
            }

            flightBooking.setUserID(rs.getInt("UserID"));
            String airlineVal = rs.getString("Airline");
            FlightBookingDto.Airline airline = FlightBookingDto.Airline.fromValue(airlineVal);
            flightBooking.setAirline(airline);


            return flightBooking;
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
