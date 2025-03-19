package com.wpi.cs509madz;

//This imports necessary SQL classes, specifically the Connection class, that allows for Java
//to connect with MySQL databases
import java.sql.*;

public class DatabaseManager {

    //The Connection object is initialized as an attribute of this class. It will be used to
    //run MySQL queries
    private final Connection db_connection;

    //This is the constructor for the DatabaseManager class. It throws an exception in the event
    //that a connection cannot be established or the incorrect username or password were provided
    public DatabaseManager(String url, String user, String password) throws SQLException {

        //The provided url, username, and password are used on a class called DriverManager, which
        //is also provided by the imported java.sql package. The static getConnection() function
        //returns a connection object that is stored in the db_connection attribute already
        //initialized
        this.db_connection = DriverManager.getConnection(url, user, password);
    }


    public int registerUser(String username, String password) throws SQLException {

        try (PreparedStatement check = db_connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {

            check.setString(1, username);
            ResultSet result = check.executeQuery();

            if (result.next() && result.getInt(1) > 0) {

                //Username already taken, return 0
                return 0;
            }
        }

        try (PreparedStatement insert = db_connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {

            insert.setString(1, username);
            insert.setString(2, password);
            int rows_affected = insert.executeUpdate();

            if (rows_affected > 0) {

                //User registration is a success, return 1
                return 1;
            }
        }

        //An error occurred, return -1
        return -1;
    }


    public boolean searchUser(String username, String password)  throws SQLException {

        try (PreparedStatement query = db_connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ? AND password = ?")) {

            query.setString(1, username);
            query.setString(2, password);

            ResultSet result = query.executeQuery();

            if (result.next()) {

                //Counts how many rows in the table matches the username and password provided
                return result.getInt(1) > 0;
            }
        }
        catch (SQLException error) {

            error.printStackTrace();
        }

        return false;
    }
}
