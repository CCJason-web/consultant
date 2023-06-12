package com.cjt.consultant.controller;

import com.cjt.consultant.model.LoginRequest;
import com.cjt.consultant.model.User;
import com.cjt.consultant.repository.UserRepository;
import com.cjt.consultant.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
public class LoginController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username).get();
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            // Authentication successful
//            String token = generateToken(user.getId());
//            return new LoginResponse(token, user.getId());
            return ResponseEntity.ok("success");
        }
        // Authentication failed
        throw new AuthenticationException("Invalid username or password");

    }

    // Validate the login credentials (example method, replace with your own logic)
    private boolean isValidCredentials(String username, String password) {
        // Add your authentication logic here
        // This is just a dummy implementation, replace it with your own authentication mechanism
        return username.equals("admin") && password.equals("password");
    }
}
