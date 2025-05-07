package com.wpi.cs509madz.model;

import com.wpi.cs509madz.service.bookingService.IBooking;

import java.util.List;

public class FlightTimeObject {
    private List<IBooking> flightPath;
    private List<Integer> flightTimes;
    private String airline;

    public FlightTimeObject(List<IBooking> flightPath, List<Integer> flightTimes, String airline) {
        this.flightPath = flightPath;
        this.flightTimes = flightTimes;
        this.airline = airline;
    }

    public List<IBooking> getFlightPath() {
        return flightPath;
    }

    public void setFlightPath(List<IBooking> flightPath) {
        this.flightPath = flightPath;
    }

    public List<Integer> getFlightTimes() {
        return flightTimes;
    }

    public void setFlightTimes(List<Integer> flightTimes) {
        this.flightTimes = flightTimes;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
