package com.wpi.cs509madz.service.authenticateService;

import com.wpi.cs509madz.service.utils.DatabaseManager;
import com.wpi.cs509madz.repository.UserRepository; // ← Make sure this is the correct path

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleMain {

    public static void main(String[] args) {

        //The following string variables are then initialized, as this is the information
        //that will be provided to the Database Manager object in order to connect to MySQL
        String db_url = "jdbc:mysql://localhost:3307/users_db";
        String db_user = "root";
        String db_password = "Joyful#83900";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(db_url);
        dataSource.setUsername(db_user);
        dataSource.setPassword(db_password);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        UserRepository userRepository = new UserRepository();
        userRepository.setJdbcTemplate(jdbcTemplate);

        DatabaseManager db_manager = new DatabaseManager(userRepository);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello, and welcome to the beta implementation of the Authenticate Class!");
        System.out.println("What would you like to do?");
        System.out.println("1. Log In");
        System.out.println("2. Sign Up");
        System.out.print("Type your response here: ");

        int choice =scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {

            System.out.print("Enter your username: ");
            String login_username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String login_password = scanner.nextLine();

            Login login = new Login(login_username, login_password, db_manager);
            login.sendRequest();
        }
        if (choice == 2) {

            System.out.print("Enter your username: ");
            String register_username = scanner.nextLine();

            System.out.print("Enter your password: (Must be more than 8 characters, feature an uppercase letter, number, and special character): ");
            String register_password = scanner.nextLine();

            Signup signup = new Signup(register_username, register_password, db_manager);
            signup.sendRequest();
        }
    }
}
