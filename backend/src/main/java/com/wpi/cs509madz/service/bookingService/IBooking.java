package com.wpi.cs509madz.service.bookingService;

import com.wpi.cs509madz.service.utils.DateTime;

import java.util.ArrayList;

public interface IBooking {
    DateTime getDepartureDate();
    DateTime getArrivalDate();
    String getArrivalLocation();

    ArrayList<ArrayList<IBooking>> calculateLayoverOptions();
    String toString();
}