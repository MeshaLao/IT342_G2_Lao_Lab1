package com.lao.userAuth.service;

import com.lao.userAuth.dto.AuthResponse;
import com.lao.userAuth.dto.LoginRequest;
import com.lao.userAuth.dto.RegisterRequest;
import com.lao.userAuth.model.User;
import com.lao.userAuth.repository.UserRepository;
import com.lao.userAuth.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        String token = tokenProvider.generateToken(savedUser);

        return new AuthResponse(token, "Registration successful! Welcome, " + savedUser.getUserName() + "!");
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("No account found with that email!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password!");
        }

        String token = tokenProvider.generateToken(user);

        return new AuthResponse(token, "Login successful! Welcome back, " + user.getUserName() + "!");
    }
}