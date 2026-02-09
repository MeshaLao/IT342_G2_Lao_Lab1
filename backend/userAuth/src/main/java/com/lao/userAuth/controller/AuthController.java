package com.lao.userAuth.controller;

import com.lao.userAuth.model.User;
import com.lao.userAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // -------------------- REGISTER --------------------
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        if(userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(409).body("Email already exists");
        }

        // encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // -------------------- LOGIN --------------------
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if(userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = userOptional.get();
        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // in a real app, generate JWT token; for now, return a simple message
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid password");
        }
    }

    // -------------------- GET PROFILE --------------------
    @GetMapping("/me")
    public ResponseEntity<User> getUserProfile(@RequestParam String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        User user = userOptional.get();
        user.setPassword(null); // hide password
        return ResponseEntity.ok(user);
    }

    // -------------------- LOGOUT --------------------
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam String email) {
        // for simplicity, just return a message
        return ResponseEntity.ok("User logged out successfully");
    }
}
