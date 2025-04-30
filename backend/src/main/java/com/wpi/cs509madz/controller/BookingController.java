package com.wpi.cs509madz.controller;

import com.wpi.cs509madz.dto.FlightRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class BookingController {

    //private final userBookingRepository
    //@Autowired
    //private BookingService bookingService;

    @PostMapping("/api/submit")
    public ResponseEntity<Map<String,Object>> submit(@RequestBody FlightRequestDto flightRequest) {
        // Will pass the parameters from flightRequest to the BookingService to allow filtering of
        // flights
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> southwestValues = new HashMap<>();
        Map<String, Object> deltas = new HashMap<>();
        //Replace functions with those that take care of
//        southwestValues.put("flightData", bookingService.GetList());
//        southwestValues.put("flightTimes", bookingService.GetTimes());
//        deltas.put("flightData", bookingService.GetList());
//        deltas.put("flightTimes", bookingService.GetTimes());

        response.put("flightResponseForTableSW", southwestValues);
        response.put("flightResponseForTableNW", deltas);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/bookFlight")
    public ResponseEntity<Map<String,Object>> bookFlight(@RequestBody FlightRequestDto flightRequest) {

    }
    @GetMapping("/api/userBookings")
    public ResponseEntity<Map<String,Object>> getUserBookings(@RequestParam int userId) {

    }
}
