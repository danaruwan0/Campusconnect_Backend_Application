package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.auth.OtpData;
import com.hivex.campusconnect.dto.auth.RegisterRequest;
import com.hivex.campusconnect.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import com.hivex.campusconnect.repo.UserRepository;
@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Map<String, OtpData> otpStorage = new HashMap<>();
    private final Map<String, RegisterRequest> pendingUsers = new HashMap<>();

    private static final long OTP_TIME = 2 * 60 * 1000; // 2 min

    // STEP 1: OTP SEND
    public String sendOtp(RegisterRequest request) {

        // email already exist check
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpData data = new OtpData();
        data.otp = otp;
        data.expiryTime = System.currentTimeMillis() + OTP_TIME;

        otpStorage.put(request.getEmail(), data);

        // pending user save (DB නෙවෙයි)
        pendingUsers.put(request.getEmail(), request);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject("OTP Verification");
        message.setText("Your OTP is: " + otp + " (valid 2 minutes)");

        mailSender.send(message);

        return "OTP sent ";
    }

    // STEP 2: OTP VERIFY + USER SAVE
    public String verifyOtpAndRegister(String email, String otp) {

        OtpData data = otpStorage.get(email);
        RegisterRequest req = pendingUsers.get(email);

        if (data == null || req == null) {
            return "Invalid request";
        }

        // expire check
        if (System.currentTimeMillis() > data.expiryTime) {
            otpStorage.remove(email);
            pendingUsers.remove(email);
            return "OTP expired";
        }

        // otp check
        if (!data.otp.equals(otp)) {
            return "Wrong OTP";
        }

        // USER SAVE NOW
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setMajor(req.getMajor());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        userRepository.save(user);

        otpStorage.remove(email);
        pendingUsers.remove(email);

        return "Registration successful";
    }





    public String resendOtp(String email) {

        RegisterRequest req = pendingUsers.get(email);

        if (req == null) {
            return "Registration session expired";
        }

        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpData data = new OtpData();
        data.otp = otp;
        data.expiryTime = System.currentTimeMillis() + OTP_TIME;

        otpStorage.put(email, data);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Verification");
        message.setText("Your new OTP is: " + otp + " (valid 2 minutes)");

        mailSender.send(message);

        return "New OTP sent successfully";
    }
}


