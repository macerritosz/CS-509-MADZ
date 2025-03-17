package com.wpi.cs509madz;

import java.util.ArrayList;

public interface IBooking {
    DateTime getArrivalDate();
    String getArrivalLocation();

    ArrayList<ArrayList<IBooking>> calculateLayoverOptions();
    String toString();
}