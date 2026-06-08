package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.auth.AuthResponse;
import com.hivex.campusconnect.dto.auth.LoginRequest;
import com.hivex.campusconnect.dto.auth.OtpRequest;
import com.hivex.campusconnect.dto.auth.RegisterRequest;
import com.hivex.campusconnect.service.OtpService;
import com.hivex.campusconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.hivex.campusconnect.dto.auth.EmailRequest;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    // register → send OTP
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return otpService.sendOtp(request);
    }

    // verify OTP → save user
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpRequest request) {
        return otpService.verifyOtpAndRegister(
                request.getEmail(),
                request.getOtp()
        );
    }

    //resend otp
    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(
            @RequestBody EmailRequest request) {

        return ResponseEntity.ok(
                otpService.resendOtp(request.getEmail())
        );
    }

    // login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}