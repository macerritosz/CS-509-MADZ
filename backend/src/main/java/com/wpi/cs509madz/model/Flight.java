package com.wpi.cs509madz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Flight {

    @Id
    private int id;

    private String departDateTime;

    private String arriveDateTime;

    private String departAirport;

    private String arriveAirport;

    private String flightNumber;

    @Override
    public String toString() {
        return id + " " + departDateTime + " " + arriveDateTime + " " + departAirport + " " + arriveAirport + " " + flightNumber;
    }
}
