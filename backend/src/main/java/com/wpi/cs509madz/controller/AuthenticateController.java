package com.wpi.cs509madz.controller;

import com.wpi.cs509madz.service.authenticateService.Authenticate;
import com.wpi.cs509madz.service.utils.DatabaseManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class AuthenticateController {

    private final DatabaseManager database_manager;

    public AuthenticateController(DatabaseManager database_manager) {

        this.database_manager = database_manager;
    }

    @PostMapping(value =  {"/api/signUp"})
    public ResponseEntity<?> signUp(@RequestBody Authenticate request) {

        int result = database_manager.registerUser(request.getUsername(), request.getPassword());

        if (result == 0) {

            return ResponseEntity.ok().body("{\"message\": \"Registration successful!\"}");
        }
        else if (result == 1) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Username already taken.\"}");
        }
        else if (result == 2) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Password does not meet requirements.\"}");
        }
        else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Database error.\"}");
        }
    }

    // Handle the sign-in request
    @PostMapping("/api/signIn")
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
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": " + e.getMessage());
        }
    }


}
