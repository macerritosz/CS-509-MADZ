package com.wpi.cs509madz.service.bookingService;

public class OneWay implements ITrip{
    IBooking booking;

    public OneWay(IBooking booking) {
        this.booking = booking;
    }

    @Override
    public void updateFlight() {

    }
}
