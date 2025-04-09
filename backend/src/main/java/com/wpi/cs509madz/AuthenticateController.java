package com.wpi.cs509madz;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.sql.SQLException;

@RestController
@RequestMapping("/") // Base URL for all endpoints
public class AuthenticateController {

    private final DatabaseManager database_manager;

    public AuthenticateController() throws SQLException {

        // Replace with your actual DB credentials
        this.database_manager = new DatabaseManager("jdbc:mysql://localhost:3307/users_db", "root", "Joyful#83900");
    }

    @PostMapping(value =  {"signUp"})
    public ResponseEntity<?> signUp(@RequestBody Authenticate request) {

        try {

            int result = database_manager.registerUser(request.getUsername(), request.getPassword());

            if (result == 0) {

                return ResponseEntity.ok().body("{\"message\": \"Registration successful!\"}");
            }
            else if (result == 1) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Username already taken.\"}");
            }
            else {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Database error.\"}");
            }
        }
        catch (SQLException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Database error.\"}");
        }
    }

    // Handle the sign-in request
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody Authenticate request) {

        try {
            boolean is_authenticated = database_manager.searchUser(request.getUsername(), request.getPassword());

            if (is_authenticated) {

                return ResponseEntity.ok().body("{\"message\": \"Login successful!\"}");
            }
            else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid username or password.\"}");
            }
        }
        catch (SQLException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Database error.\"}");
        }
    }


}
