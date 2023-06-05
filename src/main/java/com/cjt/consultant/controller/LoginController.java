package com.cjt.consultant.controller;

import com.cjt.consultant.model.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Validate the login credentials
        if (isValidCredentials(loginRequest.getUsername(), loginRequest.getPassword())) {
            // Return a success response
            return ResponseEntity.ok("Login successful");
        } else {
            // Return an error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Validate the login credentials (example method, replace with your own logic)
    private boolean isValidCredentials(String username, String password) {
        // Add your authentication logic here
        // This is just a dummy implementation, replace it with your own authentication mechanism
        return username.equals("admin") && password.equals("password");
    }
}
