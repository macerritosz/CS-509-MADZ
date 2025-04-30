package com.wpi.cs509madz.dto;

public class FlightBookingDto {
    private int tableId;

    private String departDateTime;

    private String arriveDateTime;

    private String departAirport;

    private String arriveAirport;

    private String flightNumber;

    private int referenceID_1;
    private int referenceID_2;
    private int referenceID_3;
    private int referenceID_4;

    private int userID;

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
}
