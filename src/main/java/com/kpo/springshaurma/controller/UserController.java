package com.kpo.springshaurma.controller;

import com.kpo.springshaurma.entity.User;
import com.kpo.springshaurma.repository.UserRepository;
import jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public UserController(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        User user = userRepository.findByEmail(email);

        if (user == null || !password.equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getId());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtils.getUserIdFromToken(token);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("\n" +
                            "User is not found"));

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registrationRequest) {
        try {
            if (!isValidEmail(registrationRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Incorrect email address");
            }

            if (userRepository.existsByEmail(registrationRequest.getEmail())) {
                return ResponseEntity.badRequest().body("User with this email address already exists");
            }
            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setEmail(registrationRequest.getEmail());
            user.setPasswordHash(registrationRequest.getPassword());
            user.setRole(registrationRequest.getRole());

            userRepository.save(user);

            return ResponseEntity.ok("User successfully registered");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration error");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}
