package com.wpi.cs509madz;

import java.util.ArrayList;
import java.util.Objects;

public class TestBooking implements IBooking {
    private ArrayList<TestBooking> database; // only for testing
    private String flightNumber;
    private DateTime departureDate;
    private String departureLocation;
    private DateTime arrivalDate;
    private String arrivalLocation;

    // currently used for testing, won't actually have to pass in database
    public TestBooking(ArrayList<TestBooking> database, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.database = database;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    public TestBooking(String departureLocation, String arrivalLocation) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    public TestBooking(ArrayList<TestBooking> database, String flightNumber, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.database = database;
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    public TestBooking(String flightNumber, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    public TestBooking(DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

//    following get method calls should be accessing the server
    @Override
    public DateTime getDepartureDate() {
        return departureDate;
    }

    @Override
    public DateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String getArrivalLocation() {
        return arrivalLocation;
    }

    @Override
    public ArrayList<ArrayList<IBooking>> calculateLayoverOptions() {
        ArrayList<ArrayList<IBooking>> options = new ArrayList<>();
        ArrayList<ArrayList<IBooking>> finalOptions = new ArrayList<>();
        System.out.println(departureLocation + " -> " + arrivalLocation);
        for (TestBooking booking : database) {
            if (Objects.equals(booking.departureLocation, departureLocation) && booking.getDepartureDate().isBefore(booking.getArrivalDate())) {
                ArrayList<IBooking> newList = new ArrayList<>();
                newList.add(booking);
                options.add(newList);
            }
        }

        while (!options.isEmpty()) {
            ArrayList<IBooking> cur = options.get(0);
            IBooking lastFlight = cur.get(cur.size() - 1);

//            System.out.println(cur.size() + ", " + lastFlight + " " + cur);

            for (TestBooking booking : database) {
                if (Objects.equals(lastFlight.getArrivalLocation(), booking.departureLocation)
                        && booking.getDepartureDate().isBefore(booking.getArrivalDate())
                        && lastFlight.getArrivalDate().isBefore(booking.departureDate)
                        && !Objects.equals(lastFlight.getArrivalLocation(), arrivalLocation)
                        && ((lastFlight.getArrivalDate().getDifference(booking.getDepartureDate()) > 90)
                            && (lastFlight.getArrivalDate().getDifference(booking.getDepartureDate()) < 720))
                        && cur.size() < 4) {

                    ArrayList<IBooking> newList = (ArrayList<IBooking>) cur.clone();
                    newList.add(booking);

                    if (Objects.equals(newList.get(newList.size() - 1).getArrivalLocation(), arrivalLocation)) {
                        finalOptions.add(newList);
                    } else {
                        options.add(newList);
                    }
                }
            }

            options.remove(cur);
        }

        for (ArrayList<IBooking> finalOption : finalOptions) {
            System.out.println(finalOption);
        }


        // check that none of the flights overlap but that if the flights are directly after one another (so a layover flight) that it doesnt remove that one
        return finalOptions;
    }

    @Override
    public String toString() {
        return flightNumber + ": " + departureDate + ", " + departureLocation + " -> " + arrivalDate + ", " + arrivalLocation;
    }
}