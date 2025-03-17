package com.wpi.cs509madz;

import java.util.ArrayList;
import java.util.Objects;

public class Booking implements IBooking{
    private ArrayList<Booking> database;
    private String flightNumber;
    private DateTime departureDate;
    private String departureLocation;
    private DateTime arrivalDate;
    private String arrivalLocation;

    public Booking(String departureLocation, String arrivalLocation) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    // current test
    public Booking(ArrayList<Booking> database, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.database = database;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    public Booking(ArrayList<Booking> database, String flightNumber, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.database = database;
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    public Booking(String flightNumber, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    public Booking(DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

//    following get method calls should be accessing the server
    @Override
    public String getFlightNumber() {
        return "";
    }

    @Override
    public DateTime getDepartureDate() {
        return departureDate;
    }

    @Override
    public DateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String getDepartureLocation() {
        return departureLocation;
    }

    @Override
    public String getArrivalLocation() {
        return arrivalLocation;
    }

    @Override
    public ArrayList<ArrayList<IBooking>> calculateLayoverOptions() {
        ArrayList<ArrayList<IBooking>> options = new ArrayList<>();
        System.out.println(departureLocation + ", " + arrivalLocation);
        if (departureDate == null) {
            System.out.println("pick flights on any day, not implemented");
            // pick any flights
        } else {
            for (int i = 0; i < database.size(); i++) {
                if (Objects.equals(database.get(i).departureLocation, departureLocation)) {
                    ArrayList<IBooking> newList = new ArrayList<>();
                    newList.add(database.get(i));
                    options.add(newList);
                }
            }

            for (int i = 0; i < options.size(); i++) {
                ArrayList<IBooking> cur = options.get(i);

                for (int j = 0; j < database.size(); j++) {
                    if (Objects.equals(cur.get(cur.size() - 1).getArrivalLocation(), database.get(j).departureLocation)
                            && cur.get(cur.size() - 1).getArrivalDate().isBefore(database.get(j).departureDate)
                            && !Objects.equals(cur.get(cur.size() - 1).getArrivalLocation(), arrivalLocation)
                            && cur.size() < 3) {

                        ArrayList<IBooking> newList = (ArrayList<IBooking>) cur.clone();
                        newList.add(database.get(j));

                        options.add(newList);
                    }
                }

                if (!Objects.equals(cur.get(cur.size() - 1).getArrivalLocation(), arrivalLocation)) {
                    options.remove(cur);
                }
            }

//            System.out.println("options");
            for (int i = 0; i < options.size(); i++) {
                ArrayList<IBooking> cur = options.get(i);
                if (Objects.equals(cur.get(cur.size() - 1).getArrivalLocation(), arrivalLocation)) {
                    System.out.println(cur);
                } else {
                    options.remove(cur);
                }
            }
        }

        return options;
    }

    @Override
    public String toString() {
        return flightNumber + ": " + departureDate + ", " + departureLocation + " -> " + arrivalDate + ", " + arrivalLocation;
    }
}