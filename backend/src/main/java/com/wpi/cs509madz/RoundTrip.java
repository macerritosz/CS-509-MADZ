package com.wpi.cs509madz;

public class RoundTrip implements ITrip {
    IBooking toDestination;
    IBooking fromDestination;

    public RoundTrip(IBooking toDestination, IBooking fromDestination) {
        this.toDestination = toDestination;
        this.fromDestination = fromDestination;
    }
}
