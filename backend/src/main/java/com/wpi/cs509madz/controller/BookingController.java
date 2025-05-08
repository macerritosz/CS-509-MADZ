package com.wpi.cs509madz.controller;

import com.wpi.cs509madz.dto.FlightBookingDto;
import com.wpi.cs509madz.dto.FlightRequestDto;
import com.wpi.cs509madz.model.FlightTimeObject;
import com.wpi.cs509madz.repository.DeltasRepository;
import com.wpi.cs509madz.repository.SouthwestsRepository;
import com.wpi.cs509madz.repository.UserBookings;
import com.wpi.cs509madz.service.bookingService.Booking;
import com.wpi.cs509madz.service.bookingService.IBooking;
import com.wpi.cs509madz.service.utils.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BookingController {
    @Autowired
    private SouthwestsRepository southwestsRepository;
    @Autowired
    private DeltasRepository deltasRepository;
    @Autowired
    private UserBookings userBookings;

    @PostMapping("/api/submit")
    public ResponseEntity<Map<String,Object>> submit(@RequestBody FlightRequestDto flightRequest) {
        System.out.println(flightRequest.toString());
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> allBookings = new HashMap<>();
        Map<String, Object> southwest = new HashMap<>();
        Map<String, Object> delta = new HashMap<>();

        Booking southwestBooking = new Booking(southwestsRepository.findAll(),
                new DateTime(flightRequest.getDepartureDate()),
                flightRequest.getDepartureAirport(),
                flightRequest.getArrivalAirport());

        List<List<IBooking>> swPaths = southwestBooking.findLayoverOptions(Optional.of(flightRequest.isDirectFlight()), Optional.of(flightRequest.isSameDay()));
        List<FlightTimeObject> southwestFlightPaths = new ArrayList<>();
        for (List<IBooking> path : swPaths) {
            southwestFlightPaths.add(new FlightTimeObject(path, southwestBooking.calculateLayoverTime(path), Airline.SOUTHWESTS.toString()));
        }
        southwest.put("southwestFlights", southwestFlightPaths);

        Booking deltaBooking = new Booking(deltasRepository.findAll(),
                new DateTime(flightRequest.getDepartureDate()),
                flightRequest.getDepartureAirport(),
                flightRequest.getArrivalAirport());

        List<List<IBooking>> dPaths = deltaBooking.findLayoverOptions(Optional.of(flightRequest.isDirectFlight()), Optional.of(flightRequest.isSameDay()));
        List<FlightTimeObject> deltaFlightPaths = new ArrayList<>();
        for (List<IBooking> path : dPaths) {
            deltaFlightPaths.add(new FlightTimeObject(path, deltaBooking.calculateLayoverTime(path), Airline.DELTAS.toString()));
        }
        delta.put("deltaFlights", deltaFlightPaths);

        allBookings.putAll(southwest);
        allBookings.putAll(delta);

        response.put("allBookings", allBookings);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/return-flight")
    public ResponseEntity<Map<String,Object>> returnFlights(@RequestBody FlightRequestDto flightRequest) {
        System.out.println(flightRequest.toString());
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> allBookings = new HashMap<>();
        Map<String, Object> southwest = new HashMap<>();
        Map<String, Object> delta = new HashMap<>();

        Booking southwestBooking = new Booking(southwestsRepository.findAll(),
                new DateTime(flightRequest.getDepartureDate()),
                flightRequest.getDepartureAirport(),
                flightRequest.getArrivalAirport());

        List<List<IBooking>> swPaths = southwestBooking.findLayoverOptions(Optional.of(flightRequest.isDirectFlight()), Optional.of(flightRequest.isSameDay()));
        List<FlightTimeObject> southwestFlightPaths = new ArrayList<>();
        for (List<IBooking> path : swPaths) {
            southwestFlightPaths.add(new FlightTimeObject(path, southwestBooking.calculateLayoverTime(path), Airline.SOUTHWESTS.toString()));
        }
        southwest.put("southwestFlights", southwestFlightPaths);

        Booking deltaBooking = new Booking(deltasRepository.findAll(),
                new DateTime(flightRequest.getDepartureDate()),
                flightRequest.getDepartureAirport(),
                flightRequest.getArrivalAirport());

        List<List<IBooking>> dPaths = deltaBooking.findLayoverOptions(Optional.of(flightRequest.isDirectFlight()), Optional.of(flightRequest.isSameDay()));
        List<FlightTimeObject> deltaFlightPaths = new ArrayList<>();
        for (List<IBooking> path : dPaths) {
            deltaFlightPaths.add(new FlightTimeObject(path, deltaBooking.calculateLayoverTime(path), Airline.DELTAS.toString()));
        }
        delta.put("deltaFlights", deltaFlightPaths);

        allBookings.putAll(southwest);
        allBookings.putAll(delta);

        response.put("allBookings", allBookings);

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
        System.out.println(flightRequest.toString());
        Map<String, Object> response = new HashMap<>();

        userBookings.save(flightRequest);

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
            if (Objects.equals(path.getAirline(), Airline.SOUTHWESTS.toString())) {
                List<IBooking> swPath = new ArrayList<>();
                Booking departure = new Booking(path);
                swPath.add(departure);

                List<Integer> layovers = path.getReferenceIDs();
                for (Integer flightNum : layovers) {
                    if (layovers.get(flightNum) != null) {
                        swPath.add(new Booking(swRepo.getFlightByID(layovers.get(flightNum)).get(0)));
                    }
                }

                southwest.put("flight data", new FlightTimeObject(swPath, departure.calculateLayoverTime(swPath), Airline.SOUTHWESTS.toString()));
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

                delta.put("flight data", new FlightTimeObject(dPath, departure.calculateLayoverTime(dPath), Airline.DELTAS.toString()));
            }
        }

        response.putAll(southwest);
        response.putAll( delta);
        return ResponseEntity.ok(response);
    }

    public enum Airline {
        DELTAS("deltas"),
        SOUTHWESTS("southwests");

        private final String value;

        Airline(String value) {
            this.value = value;
        }
    }
}
