package com.wpi.cs509madz.controller;

import com.wpi.cs509madz.dto.FlightBookingDto;
import com.wpi.cs509madz.dto.FlightRequestDto;
import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.repository.DeltasRepository;
import com.wpi.cs509madz.repository.SouthwestsRepository;
import com.wpi.cs509madz.repository.UserBookings;
import com.wpi.cs509madz.service.bookingService.Booking;
import com.wpi.cs509madz.service.bookingService.IBooking;
import com.wpi.cs509madz.service.utils.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public class BookingController {
    private SouthwestsRepository southwestsRepository;
    private DeltasRepository deltasRepository;

    // TODO add total flight time
    @PostMapping("/api/submit")
    public ResponseEntity<Map<String,Object>> submit(@RequestBody FlightRequestDto flightRequest) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> southwest = new HashMap<>();
        Map<String, Object> delta = new HashMap<>();

        Booking southwestBooking = new Booking(southwestsRepository.findAll(),
                new DateTime(flightRequest.getDepartureDate()),
                flightRequest.getDepartureAirport(),
                flightRequest.getArrivalAirport());

        List<List<IBooking>> swPaths = southwestBooking.findLayoverOptions(Optional.of(flightRequest.isDirectFlight()), Optional.of(flightRequest.isSameDay()));
        for (List<IBooking> path : swPaths) {
            southwest.put("southwestFlights", new FlightTimeObject(path, southwestBooking.calculateLayoverTime(path)));
        }

        Booking deltaBooking = new Booking(deltasRepository.findAll(),
                new DateTime(flightRequest.getDepartureDate()),
                flightRequest.getDepartureAirport(),
                flightRequest.getArrivalAirport());

        List<List<IBooking>> dPaths = deltaBooking.findLayoverOptions(Optional.of(flightRequest.isDirectFlight()), Optional.of(flightRequest.isSameDay()));
        for (List<IBooking> path : dPaths) {
            southwest.put("deltaFlights", new FlightTimeObject(path, deltaBooking.calculateLayoverTime(path)));
        }

        response.put("flightResponseForTableSW", southwest);
        response.put("flightResponseForTableNW", delta);

        return ResponseEntity.ok(response);
    }

    /**
     * Receives a flightBooking to be saved into the database,
     * Create a function in userBookingRepository to save a flight with the corresponding data and foreign keys to Database
     * @param flightRequest a FlightBookingDto to be created when a post request is received
     * @return a ResponseEntity that confirms that it was saved to database.
     */
    @PostMapping("/api/bookFlight")
    public ResponseEntity<Map<String,Object>> bookFlight(@RequestBody FlightBookingDto flightRequest) {
        Map<String, Object> response = new HashMap<>();

        response.put("bookedFlight",
                new Booking(
                        Integer.toString(flightRequest.getTableId()),
                        new DateTime(flightRequest.getDepartDateTime()),
                        flightRequest.getDepartAirport(),
                        new DateTime(flightRequest.getArriveDateTime()),
                        flightRequest.getArriveAirport()));

        return ResponseEntity.ok(response);
    }

    /**
     Receives a userID used to identify a user and calls the userBookingRepository to find and return all bookings for that user
     @param userId an ID corresponding to a user to find their bookings
     @return a ResponseEntity that contains all the data requested
     */
    @GetMapping("/api/userBookings/{userId}")
    public ResponseEntity<Map<String,Object>> getUserBookings(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> southwest = new HashMap<>();
        Map<String, Object> delta = new HashMap<>();

        SouthwestsRepository swRepo = new SouthwestsRepository();
        DeltasRepository dRepo = new DeltasRepository();

        UserBookings userBookings = new UserBookings();
        List<FlightBookingDto> flightPaths = userBookings.getAllFlightsByUserID(Integer.parseInt(userId));
        for (FlightBookingDto path : flightPaths) {
            if (path.getAirline() == FlightBookingDto.Airline.SOUTHWESTS) {
                List<IBooking> swPath = new ArrayList<>();
                Booking departure = new Booking(path);
                swPath.add(departure);

                List<Integer> layovers = path.getReferenceIDs();
                for (Integer flightNum : layovers) {
                    if (layovers.get(flightNum) != null) {
                        swPath.add(new Booking(swRepo.getFlightByID(layovers.get(flightNum)).get(0)));
                    }
                }

                southwest.put("flight data", new FlightTimeObject(swPath, departure.calculateLayoverTime(swPath)));
            } else {
                List<IBooking> dPath = new ArrayList<>();
                Booking departure = new Booking(path);
                dPath.add(departure);

                List<Integer> layovers = path.getReferenceIDs();
                for (Integer flightNum : layovers) {
                    if (layovers.get(flightNum) != null) {
                        dPath.add(new Booking(dRepo.getFlightByID(layovers.get(flightNum)).get(0)));
                    }
                }

                delta.put("flight data", new FlightTimeObject(dPath, departure.calculateLayoverTime(dPath)));
            }
        }

        response.put("southwestBookings", southwest);
        response.put("deltaBookings", delta);
        return ResponseEntity.ok(response);
    }

    private class FlightTimeObject {
        List<IBooking> flightPath;
        List<Integer> flightTimes;

        FlightTimeObject(List<IBooking> flightPath, List<Integer> flightTimes) {
            this.flightPath = flightPath;
            this.flightTimes = flightTimes;
        }
    }
}
