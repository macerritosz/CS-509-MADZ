package com.wpi.cs509madz.dto;

public class FlightRequestDto {
    private boolean oneWay;
    private boolean roundTrip;
    private boolean isSameDay;
    private boolean isDirect;
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String arrivalDate;

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public boolean isSameDay() {
        return isSameDay;
    }

    public void setSameDay(boolean sameDay) {
        this.isSameDay = sameDay;
    }

    public boolean isDirectFlight() {
        return isDirect;
    }

    public void setDirectFlight(boolean directFlight) {
        this.isDirect = directFlight;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }


    @Override
    public String toString() {
        return oneWay + " " + roundTrip + " " + isSameDay + " " + isDirect + " " + departureAirport + " " + arrivalAirport + " " + departureDate + " " + arrivalDate;
    }
}
