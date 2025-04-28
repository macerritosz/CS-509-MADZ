package com.wpi.cs509madz.service.bookingService;

import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.service.utils.DateTime;

import java.util.*;

public class Booking implements IBooking {
    private List<Flight> database;
    private List<Booking> flightDatabase;
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

        flightDatabase = database.stream()
                .map(flight -> new Booking(
                        flight.getFlightNumber(),
                        flight.getDepartDateTime(),
                        flight.getDepartAirport(),
                        flight.getArriveDateTime(),
                        flight.getArriveAirport()))
                .filter(booking -> booking.getDepartureDate().isBefore(booking.getArrivalDate()))
                .toList();
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
        return calculateLayoverOptions(Optional.empty(), Optional.empty());
    }

    public List<List<IBooking>> calculateLayoverOptions(Optional<Boolean> direct, Optional<Boolean> sameDay) {
        boolean isDirect = direct.orElse(false);
        boolean isSameDay = sameDay.orElse(false);

        if (direct.isEmpty() && sameDay.isEmpty()) {
            return calculateLayover();
        } else if (isDirect && !isSameDay) {
            return calculateDirect();
        } else if (!isDirect && isSameDay) {
            return calculateSameDay();
        } else if (isDirect && isSameDay) {
            return calculateBoth();
        } else {
            return calculateLayover();
        }
    }

    private List<List<IBooking>> calculateLayover() {
        Queue<List<IBooking>> options = new LinkedList<>();
        List<List<IBooking>> finalOptions = new ArrayList<>();

        for (Booking booking : flightDatabase) {
            if (booking.getDepartureLocation().equals(departureLocation)) {
                options.offer(List.of(booking));
            }
        }

        while (!options.isEmpty()) {
            List<IBooking> curPath = options.poll();
            IBooking lastFlight = curPath.get(curPath.size() - 1);

            for (Booking booking : flightDatabase) {
                if (curPath.size() < 4 &&
                        Objects.equals(lastFlight.getArrivalLocation(), booking.getDepartureLocation())
                        && booking.getDepartureDate().isBefore(booking.getArrivalDate())
                        && lastFlight.getArrivalDate().isBefore(booking.getDepartureDate())
                        && !Objects.equals(lastFlight.getArrivalLocation(), arrivalLocation)
                        && ((lastFlight.getArrivalDate().getDifference(booking.getDepartureDate()) > 90)
                        && (lastFlight.getArrivalDate().getDifference(booking.getDepartureDate()) < 240))) {

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

    private List<List<IBooking>> calculateDirect() {
        List<List<IBooking>> finalOptions = new ArrayList<>();

        for (Booking booking : flightDatabase) {
            if (booking.getDepartureLocation().equals(departureLocation) && booking.getArrivalLocation().equals(arrivalLocation)) {
                finalOptions.add(List.of(booking));
            }
        }

        return finalOptions;
    }

    private List<List<IBooking>> calculateSameDay() {
        Queue<List<IBooking>> options = new LinkedList<>();
        List<List<IBooking>> finalOptions = new ArrayList<>();

        for (Booking booking : flightDatabase) {
            if (booking.getDepartureLocation().equals(departureLocation)) {
                options.offer(List.of(booking));
            }
        }

        while (!options.isEmpty()) {
            List<IBooking> curPath = options.poll();
            IBooking lastFlight = curPath.get(curPath.size() - 1);

            for (Booking booking : flightDatabase) {
                if (Objects.equals(lastFlight.getArrivalLocation(), booking.getDepartureLocation())
                        && booking.getDepartureDate().isBefore(booking.getArrivalDate())
                        && lastFlight.getArrivalDate().equals(booking.getDepartureDate())
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

    private List<List<IBooking>> calculateBoth() {
        List<List<IBooking>> finalOptions = new ArrayList<>();

        for (Booking booking : flightDatabase) {
            if (booking.getDepartureLocation().equals(departureLocation)
                    && booking.getArrivalLocation().equals(arrivalLocation)
                    && departureDate.equals(booking.getDepartureDate())) {
                finalOptions.add(List.of(booking));
            }
        }

        return finalOptions;
    }

    @Override
    public String toString() {
        return flightNumber + ": " + departureDate + ", " + departureLocation + " -> " + arrivalDate + ", " + arrivalLocation;
    }
}
