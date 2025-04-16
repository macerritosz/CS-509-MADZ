package com.wpi.cs509madz;

import com.wpi.cs509madz.model.Flight;
import com.wpi.cs509madz.repository.DeltasRepository;
import com.wpi.cs509madz.service.bookingService.TestBooking;
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

//        for (int i = 0; i < flightDatabase.size(); i++) {
//            System.out.println(flightDatabase);
//        }
//        ArrayList<TestBooking> database = new ArrayList<>();
//
//        // Connection URL
//        String url = "jdbc:mysql://localhost:3306/flightdata";
//        String user = "root";
//        String password = "Awesome19*";
//
//        // Establish a connection
//        try (Connection conn = DriverManager.getConnection(url, user, password)) {
//            // Set the connection to read-only mode
//            conn.setReadOnly(true);
//
//            // Sample query to fetch data
//            String query = "SELECT * FROM deltas";
//            try (Statement stmt = conn.createStatement();
//                 ResultSet rs = stmt.executeQuery(query)) {
//
//                // Process the results
//                while (rs.next()) {
//                    TestBooking booking = new TestBooking(rs.getString("FlightNumber"), new DateTime(rs.getString("DepartDateTime")),
//                            rs.getString("DepartAirport"),
//                            new DateTime(rs.getString("ArriveDateTime")),
//                            rs.getString("ArriveAirport"));
//                    database.add(booking);
//                }
//
//            } catch (SQLException e) {
//                System.out.println("Error executing query: " + e.getMessage());
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Connection failed: " + e.getMessage());
//        }
//
//        System.out.println();

        TestBooking test = new TestBooking(flightDatabase,
                new DateTime("2022-12-26"), "Salt Lake City (SLC)",
                new DateTime("2023-12-27"), "Sioux Falls (FSD)");
        test.calculateLayoverOptions();
    }
}
