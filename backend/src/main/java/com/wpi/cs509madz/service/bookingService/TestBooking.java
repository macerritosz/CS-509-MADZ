package com.wpi.cs509madz.service.bookingService;

import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.service.utils.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestBooking implements IBooking {
    private List<Flight> flightDatabase; // only for testing
    private String flightNumber;
    private DateTime departureDate;
    private String departureLocation;
    private DateTime arrivalDate;
    private String arrivalLocation;

    // currently used for testing, won't actually have to pass in database
    public TestBooking(List<Flight> flightDatabase, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.flightDatabase = flightDatabase;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    public TestBooking(String departureLocation, String arrivalLocation) {
        this.departureLocation = departureLocation;
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
    public String getDepartureLocation() {
        return departureLocation;
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
    public List<List<IBooking>> calculateLayoverOptions() {
        ArrayList<TestBooking> database = new ArrayList<>();
        for (int i = 0; i < flightDatabase.size(); i++) {
            Flight cur = flightDatabase.get(i);
            database.add(
                    new TestBooking(cur.getDepartDateTime(), cur.getDepartAirport(), cur.getArriveDateTime(), cur.getArriveAirport())
            );
        }

        ArrayList<ArrayList<IBooking>> options = new ArrayList<>();
        List<List<IBooking>> finalOptions = new ArrayList<>();
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

        for (List<IBooking> finalOption : finalOptions) {
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