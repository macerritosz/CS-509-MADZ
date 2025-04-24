package com.wpi.cs509madz;

import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.repository.DeltasRepository;
import com.wpi.cs509madz.service.bookingService.Booking;
import com.wpi.cs509madz.service.bookingService.IBooking;
import com.wpi.cs509madz.service.utils.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.wpi.cs509madz.repository")
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        DeltasRepository deltasRepository = context.getBean(DeltasRepository.class);
        List<Flight> flightDatabase = deltasRepository.findAll();

        System.out.println("Salt Lake City (SLC)" + " -> " + "Sioux Falls (FSD)");
        Booking test = new Booking(flightDatabase,
                new DateTime("2022-12-26"), "Salt Lake City (SLC)",
                new DateTime("2023-12-27"), "Sioux Falls (FSD)");
        List<List<IBooking>> flightPaths = test.calculateLayoverOptions();
        for (List<IBooking> booking : flightPaths) {
            System.out.println(booking.toString());
        }

    }
}
