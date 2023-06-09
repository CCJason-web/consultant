package com.cjt.consultant.controller;

import com.cjt.consultant.model.SignupRequest;
import com.cjt.consultant.model.User;
import com.cjt.consultant.repository.UserRepository;
import com.cjt.consultant.service.serviceimpl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/v1/api")
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) throws KeyManagementException, NoSuchAlgorithmException, MessagingException {

        // Create a new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setEmail(signupRequest.getEmail());

        // Check if the username is already taken
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        String verificationLink = "https://example.com/verify?token=";
        String emailSubject = "Email Verification";
        String emailBody = "Please click the following link to verify your email: " + verificationLink;

        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);

        // Save the user in the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

}
