package com.wpi.cs509madz.model;

import com.wpi.cs509madz.service.utils.DateTime;
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



    public void setId(int id) {
        this.id = id;
    }

    public void setDepartDateTime(String departDateTime) {
        this.departDateTime = departDateTime;
    }

    public void setArriveDateTime(String arriveDateTime) {
        this.arriveDateTime = arriveDateTime;
    }

    public void setDepartAirport(String departAirport) {
        this.departAirport = departAirport;
    }

    public void setArriveAirport(String arriveAirport) {
        this.arriveAirport = arriveAirport;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public DateTime getDepartDateTime() {
        return new DateTime(departDateTime);
    }

    public DateTime getArriveDateTime() {
        return new DateTime(arriveDateTime);
    }

    public String getDepartAirport() {
        return departAirport;
    }

    public String getArriveAirport() {
        return arriveAirport;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    // when formatting, add a t to the json for separation
    @Override
    public String toString() {
        return flightNumber + " " + departDateTime + " " + arriveDateTime + " " + departAirport + " " + arriveAirport + " " + flightNumber;
    }
}
