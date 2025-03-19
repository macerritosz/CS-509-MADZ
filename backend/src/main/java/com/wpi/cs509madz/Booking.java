package com.wpi.cs509madz;

import java.util.ArrayList;
import java.util.Date;

public class Booking implements IBooking {
    private String flightNumber;
    private DateTime departureDate;
    private String departureLocation;
    private DateTime arrivalDate;
    private String arrivalLocation;

    public Booking(String departureLocation, String arrivalLocation) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    public Booking(DateTime departureDate, String departureLocation, String arrivalLocation) {
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    public Booking(String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    @Override
    public DateTime getDepartureDate() {
        return departureDate;
    }

    @Override
    public DateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String getArrivalLocation() {
        return arrivalLocation;
    }

    @Override
    public ArrayList<ArrayList<IBooking>> calculateLayoverOptions() {
        if (arrivalDate == null) {
            // only care about the day/time the leave
        } else if (departureDate == null) {
            // only care about the day/time they arrive
        } else {
            // don't care the day/time they arrive/leave
        }

        // same logic as testBooking but using the database directly to get information rather than something passed around
        return null;
    }

    @Override
    public String toString() {
        return flightNumber + ": " + departureDate + ", " + departureLocation + " -> " + arrivalDate + ", " + arrivalLocation;
    }
}
