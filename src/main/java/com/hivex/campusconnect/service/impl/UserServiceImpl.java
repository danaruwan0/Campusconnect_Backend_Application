package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.dto.auth.AuthResponse;
import com.hivex.campusconnect.dto.auth.LoginRequest;
import com.hivex.campusconnect.dto.auth.RegisterRequest;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.security.JwtUtil;
import com.hivex.campusconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User register(RegisterRequest request) {

        if (!request.getEmail().endsWith("@icst.edu.lk")) {
            throw new RuntimeException("Use your university email");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMajor(request.getMajor());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }



}