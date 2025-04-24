package com.wpi.cs509madz.service.bookingService;

import com.wpi.cs509madz.service.utils.DateTime;

import java.util.List;

public interface IBooking {
    DateTime getDepartureDate();
    String getDepartureLocation();
    DateTime getArrivalDate();
    String getArrivalLocation();

    List<List<IBooking>> calculateLayoverOptions();
    String toString();
}