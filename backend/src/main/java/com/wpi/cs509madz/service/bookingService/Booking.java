package com.wpi.cs509madz.service.bookingService;

import com.wpi.cs509madz.dto.FlightBookingDto;
import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.service.utils.DateTime;

import java.util.*;

public class Booking implements IBooking {
    private List<Booking> flightDatabase;
    private String flightNumber;
    private DateTime departureDate;
    private String departureLocation;
    private DateTime arrivalDate;
    private String arrivalLocation;

    private List<List<IBooking>> layovers;

    public Booking(List<Booking> database, DateTime departureDate,String departureLocation, String arrivalLocation) {
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;

        database.removeIf(booking -> booking.getDepartureDateTime().isBefore(departureDate));

        flightDatabase = database;
    }

    public Booking(Flight flight) {
        this.flightNumber = flight.getFlightNumber();
        this.departureDate = getDepartureDate();
        this.departureLocation = getDepartureLocation();
        this.arrivalDate = getArrivalDateTime();
        this.arrivalLocation = getArrivalLocation();
    }

    public Booking (FlightBookingDto flight) {
        this.flightNumber = flight.getFlightNumber();
        this.departureDate = new DateTime(flight.getDepartDateTime());
        this.departureLocation = flight.getDepartAirport();
        this.arrivalDate = new DateTime(flight.getArriveDateTime());
        this.arrivalLocation = flight.getArriveAirport();
    }

    public Booking(String flightNumber, DateTime departureDate, String departureLocation, DateTime arrivalDate, String arrivalLocation) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureLocation = departureLocation;
        this.arrivalDate = arrivalDate;
        this.arrivalLocation = arrivalLocation;
    }

    @Override
    public DateTime getDepartureDateTime() {
        return departureDate;
    }

    public DateTime getDepartureDate() {
        return new DateTime(departureDate.getYear() + "-" + departureDate.getMonth() + "-" + departureDate.getDay());
    }

    @Override
    public String getDepartureLocation() {
        return departureLocation;
    }

    @Override
    public DateTime getArrivalDateTime() {
        return arrivalDate;
    }

    public DateTime getArrivalDate() {
        return new DateTime(arrivalDate.getYear() + "-" + arrivalDate.getMonth() + "-" + arrivalDate.getDay());
    }

    @Override
    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public List<List<IBooking>> findLayoverOptions() {
        return findLayoverOptions(Optional.empty(), Optional.empty());
    }

    public List<List<IBooking>> findLayoverOptions(Optional<Boolean> direct, Optional<Boolean> sameDay) {
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

    private List<List<IBooking>> calculateDirect() {
        List<List<IBooking>> finalOptions = new ArrayList<>();

        for (Booking booking : flightDatabase) {
            if (booking.getDepartureDate().toString().equals(departureDate.toString()) &&
                    booking.getDepartureLocation().equals(departureLocation) &&
                    booking.getArrivalLocation().equals(arrivalLocation)) {
                finalOptions.add(List.of(booking));
            }
        }

        layovers = finalOptions;
        return finalOptions;
    }

    private List<List<IBooking>> calculateLayover() {
        Queue<List<IBooking>> options = new LinkedList<>();
        List<List<IBooking>> finalOptions = new ArrayList<>();

        List<List<IBooking>> direct = calculateDirect();
        finalOptions.addAll(direct);

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
                        && booking.getDepartureDateTime().isBefore(booking.getArrivalDateTime())
                        && lastFlight.getArrivalDateTime().isBefore(booking.getDepartureDateTime())
                        && !Objects.equals(lastFlight.getArrivalLocation(), arrivalLocation)
                        && ((lastFlight.getArrivalDateTime().getDifference(booking.getDepartureDateTime()) > 90)
                        && (lastFlight.getArrivalDateTime().getDifference(booking.getDepartureDateTime()) < 240))) {

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

        layovers = finalOptions;
        return finalOptions;
    }

    private List<List<IBooking>> calculateSameDay() {
        Queue<List<IBooking>> options = new LinkedList<>();
        List<List<IBooking>> finalOptions = new ArrayList<>();

        List<List<IBooking>> direct = calculateDirect();
        finalOptions.addAll(direct);

        for (Booking booking : flightDatabase) {
            if (booking.getDepartureLocation().equals(departureLocation)) {
                options.offer(List.of(booking));
            }
        }

        while (!options.isEmpty()) {
            List<IBooking> curPath = options.poll();
            IBooking lastFlight = curPath.get(curPath.size() - 1);

            for (Booking booking : flightDatabase) {
                if (curPath.size() < 4
                        && Objects.equals(lastFlight.getArrivalLocation(), booking.getDepartureLocation())
                        && booking.getDepartureDateTime().isBefore(booking.getArrivalDateTime())
                        && lastFlight.getArrivalDateTime().isBefore(booking.getDepartureDateTime())
                        && !Objects.equals(lastFlight.getArrivalLocation(), arrivalLocation)
                        && ((lastFlight.getArrivalDateTime().getDifference(booking.getDepartureDateTime()) > 90)
                        && (lastFlight.getArrivalDateTime().getDifference(booking.getDepartureDateTime()) < 240))) {

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
                    && departureDate.equals(booking.getDepartureDateTime())) {
                finalOptions.add(List.of(booking));
            }
        }

        layovers = finalOptions;
        return finalOptions;
    }

    public List<Integer> calculateLayoverTime(List<IBooking> booking) {
        List<Integer> layoverTimes = new ArrayList<>();

        int totalTime = 0;
        for (int i = 0; i < booking.size() - 1; i++) {
            IBooking cur = booking.get(i);
            totalTime += cur.getDepartureDateTime().getDifference(cur.getArrivalDateTime());
        }

        for (int i = 0; i < booking.size() - 2; i++) {
            int time = booking.get(i).getArrivalDateTime().getDifference(booking.get(i + 1).getDepartureDateTime());
            totalTime += time;
            layoverTimes.add(time);
        }

        layoverTimes.add(0, totalTime);

        return layoverTimes;
    }

    @Override
    public String toString() {
        return flightNumber + ": " + departureDate + ", " + departureLocation + " -> " + arrivalDate + ", " + arrivalLocation;
    }
}
