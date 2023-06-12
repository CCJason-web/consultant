package com.cjt.consultant.controller;

import com.cjt.consultant.model.EmailRequest;
import com.cjt.consultant.model.SignupRequest;
import com.cjt.consultant.model.User;
import com.cjt.consultant.model.VerificationCode;
import com.cjt.consultant.repository.UserRepository;
import com.cjt.consultant.service.serviceimpl.EmailService;
import com.cjt.consultant.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api")
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private RedisTemplate<String, VerificationCode> redisTemplate;

    public SignupController(RedisTemplate<String, VerificationCode> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) throws KeyManagementException, NoSuchAlgorithmException, MessagingException {

        // Create a new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(PasswordUtil.hashPassword(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());

        VerificationCode verificationCode = redisTemplate.opsForValue().get(signupRequest.getEmail());

        if (verificationCode == null) {
            return ResponseEntity.badRequest().body("Verification code not sent");
        }

        if (!verificationCode.getCode().equals(signupRequest.getVerificationCode())) {
            return ResponseEntity.badRequest().body("Incorrect verification code");
        }

        if (verificationCode.isExpired()) {
            return ResponseEntity.badRequest().body("Verification code has expired");
        }

        // Check if the username is already taken
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Save the user in the database
        userRepository.save(user);

        redisTemplate.opsForValue().getAndDelete(signupRequest.getEmail());

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/emailVerification")
    public ResponseEntity<String> sendVerificationCode(@RequestBody EmailRequest emailRequest){
        String verificationCode = generateVerificationCode();

        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        String emailSubject = "Email Verification";
        String emailBody = "Please use the following Code to verify your email: \n" + verificationCode;

        VerificationCode code = new VerificationCode(verificationCode, expirationTime);

        redisTemplate.opsForValue().set(emailRequest.getEmail(), code, Duration.ofMinutes(5));
        emailService.sendEmail(emailRequest.getEmail(), emailSubject, emailBody);

        return ResponseEntity.ok("Verification code sent");
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

}
