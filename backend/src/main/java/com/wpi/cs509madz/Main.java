package com.wpi.cs509madz;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Booking> database = new ArrayList<>();

        // Connection URL
        String url = "jdbc:mysql://localhost:3306/flightdata";
        String user = "root";      // MySQL username
        String password = "Awesome19*";  // MySQL password

        // Establish a connection
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Set the connection to read-only mode
            conn.setReadOnly(true);

            // Sample query to fetch data
            String query = "SELECT * FROM deltas";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

//                System.out.println("ID\tDepartDateTime\tArriveDateTime\tDepartAirpot\tArriveAirport\tFlightNumber");

                // Process the results
                while (rs.next()) {
                    Booking booking = new Booking(rs.getString("FlightNumber"), new DateTime(rs.getString("DepartDateTime")),
                            rs.getString("DepartAirport"),
                            new DateTime(rs.getString("ArriveDateTime")),
                            rs.getString("ArriveAirport"));
                    database.add(booking);

//                    System.out.println(rs.getString("Id") + "\t" +
//                            rs.getString("DepartDateTime") + "\t" +
//                            rs.getString("ArriveDateTime") + "\t" +
//                            rs.getString("DepartAirport") + "\t" +
//                            rs.getString("ArriveAirport") + "\t" +
//                            rs.getString("FlightNumber") + "\t");
                }

            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        System.out.println();
//        DateTime date1 = new DateTime(2022, 12, 27, 15, 7, 0);
//        DateTime date2 = new DateTime(2022, 12, 27, 12, 0, 0);
//        System.out.println(date1.isBefore(date2));
//        System.out.println(date2.isBefore(date1));


        Booking test = new Booking(database,
                new DateTime("2022-12-27"), "New York (EWR)",
                new DateTime("2022-12-27"), "Tulsa (TUL)");
        test.calculateLayoverOptions();
    }
}
