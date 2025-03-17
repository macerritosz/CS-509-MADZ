package com.wpi.cs509madz;

import java.util.ArrayList;

public interface IBooking {
    public String getFlightNumber();
    public DateTime getDepartureDate();
    public DateTime getArrivalDate();
    public String getDepartureLocation();
    public String getArrivalLocation();

    ArrayList<ArrayList<IBooking>> calculateLayoverOptions();
    String toString();
}