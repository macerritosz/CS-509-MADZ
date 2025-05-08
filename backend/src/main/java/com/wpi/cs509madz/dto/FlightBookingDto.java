package com.wpi.cs509madz.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightBookingDto {
    private int tableId;

    private String departDateTime;

    private String arriveDateTime;

    private String departAirport;

    private String arriveAirport;

    private String flightNumber;

    private List<Integer> referenceIDs = new ArrayList<>(Arrays.asList(null, null, null));

    private int userID;

    private String airline;  // Changed from Airline enum to String

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getDepartDateTime() {
        return departDateTime;
    }

    public void setDepartDateTime(String departDateTime) {
        this.departDateTime = departDateTime;
    }

    public String getArriveDateTime() {
        return arriveDateTime;
    }

    public void setArriveDateTime(String arriveDateTime) {
        this.arriveDateTime = arriveDateTime;
    }

    public String getDepartAirport() {
        return departAirport;
    }

    public void setDepartAirport(String departAirport) {
        this.departAirport = departAirport;
    }

    public String getArriveAirport() {
        return arriveAirport;
    }

    public void setArriveAirport(String arriveAirport) {
        this.arriveAirport = arriveAirport;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Integer> getReferenceIDs() {
        return referenceIDs;
    }

    public void setReferenceIDs(List<Integer> referenceIDs) {
        this.referenceIDs = referenceIDs;
    }

    @Override
    public String toString() {
        return "FlightBookingDto{" +
                "tableId=" + tableId +
                ", departDateTime='" + departDateTime + '\'' +
                ", arriveDateTime='" + arriveDateTime + '\'' +
                ", departAirport='" + departAirport + '\'' +
                ", arriveAirport='" + arriveAirport + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", referenceIDs=" + referenceIDs +
                ", userID=" + userID +
                ", airline='" + airline + '\'' +  // Adjusted to String
                '}';
    }
}
