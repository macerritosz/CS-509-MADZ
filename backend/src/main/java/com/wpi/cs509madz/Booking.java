package com.wpi.cs509madz;

import java.util.ArrayList;

public class Booking implements IBooking {
    @Override
    public DateTime getArrivalDate() {
        return null;
    }

    @Override
    public String getArrivalLocation() {
        return "";
    }

    @Override
    public ArrayList<ArrayList<IBooking>> calculateLayoverOptions() {
        return null;
    }
}
