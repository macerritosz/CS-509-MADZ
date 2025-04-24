package com.wpi.cs509madz.service.bookingService;

import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.service.utils.DateTime;

import java.util.*;

public class Booking implements IBooking {
    private List<Flight> database;
    private String flightNumber;
    private DateTime departureDate;
    private String departureLocation;
    private DateTime arrivalDate;
    private String arrivalLocation;

    public Booking(List<Flight> database, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.database = database;
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
        List<Booking> curDatabase = database.stream()
                .map(flight -> new Booking(
                        flight.getFlightNumber(),
                        flight.getDepartDateTime(),
                        flight.getDepartAirport(),
                        flight.getArriveDateTime(),
                        flight.getArriveAirport()))
                .filter(booking -> booking.getDepartureDate().isBefore(booking.getArrivalDate()))
                .toList();

        Queue<List<IBooking>> options = new LinkedList<>();
        List<List<IBooking>> finalOptions = new ArrayList<>();

        System.out.println(departureLocation + " -> " + arrivalLocation);

        for (Booking booking : curDatabase) {
            if (booking.getDepartureLocation().equals(departureLocation)) {
                options.offer(List.of(booking));
            }
        }

        while (!options.isEmpty()) {
            List<IBooking> curPath = options.poll();
            IBooking lastFlight = curPath.get(curPath.size() - 1);

            for (Booking booking : curDatabase) {
                if (Objects.equals(lastFlight.getArrivalLocation(), booking.getDepartureLocation())
                        && booking.getDepartureDate().isBefore(booking.getArrivalDate())
                        && lastFlight.getArrivalDate().isBefore(booking.getDepartureDate())
                        && !Objects.equals(lastFlight.getArrivalLocation(), arrivalLocation)
                        && ((lastFlight.getArrivalDate().getDifference(booking.getDepartureDate()) > 90)
                        && (lastFlight.getArrivalDate().getDifference(booking.getDepartureDate()) < 240))
                        && curPath.size() < 4) {

                    List<IBooking> newPath = new ArrayList<>(curPath);
                    newPath.add(booking);

                    if (Objects.equals(newPath.get(newPath.size() - 1).getArrivalLocation(), arrivalLocation)) {
                        finalOptions.add(newPath);
                    } else {
                        options.add(newPath);
                    }
                }
            }
        }

        return finalOptions;
    }

    @Override
    public String toString() {
        return flightNumber + ": " + departureDate + ", " + departureLocation + " -> " + arrivalDate + ", " + arrivalLocation;
    }
}
