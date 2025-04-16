package com.wpi.cs509madz.service.bookingService;

import com.wpi.cs509madz.service.utils.DateTime;

import java.util.ArrayList;

public class Booking implements IBooking {
    private String flightNumber;
    private DateTime departureDate;
    private String departureLocation;
    private DateTime arrivalDate;
    private String arrivalLocation;

    public Booking(DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.departureDate = departureDate;
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

        // same logic as testBooking but using the database directly to get information rather than something passed around
        return null;
    }

    @Override
    public String toString() {
        return flightNumber + ": " + departureDate + ", " + departureLocation + " -> " + arrivalDate + ", " + arrivalLocation;
    }
}
