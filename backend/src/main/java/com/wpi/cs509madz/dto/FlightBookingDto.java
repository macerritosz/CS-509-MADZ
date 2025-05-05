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

    private Airline airline;

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
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

    public enum Airline {
        DELTAS("deltas"),
        SOUTHWESTS("southwests");

        private final String value;

        Airline(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Airline fromValue(String value) {
            for (Airline airline : Airline.values()) {
                if (airline.value.equalsIgnoreCase(value)) {
                    return airline;
                }
            }
            throw new IllegalArgumentException("Unknown airline: " + value);
        }
    }
}
