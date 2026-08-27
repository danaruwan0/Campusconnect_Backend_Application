package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.auth.OtpData;
import com.hivex.campusconnect.dto.auth.RegisterRequest;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.entity.UserProfile;
import com.hivex.campusconnect.repo.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//new import
import com.hivex.campusconnect.dto.auth.AuthResponse;
import com.hivex.campusconnect.security.JwtUtil;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import com.hivex.campusconnect.repo.UserRepository;
@Service
public class OtpService {

    //jwt part
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //new temp prophle  create
    @Autowired
    private UserProfileRepository profileRepository;



    private final Map<String, OtpData> otpStorage = new HashMap<>();
    private final Map<String, RegisterRequest> pendingUsers = new HashMap<>();

    private static final long OTP_TIME = 5 * 60 * 1000; // 2 min

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
        message.setText("Your OTP is: " + otp + " (valid 5 minutes)");

        mailSender.send(message);

        return "OTP sent ";
    }

    // STEP 2: OTP VERIFY + USER SAVE

    //chane meth line 1
    public AuthResponse verifyOtpAndRegister(String email, String otp) {

        OtpData data = otpStorage.get(email);
        RegisterRequest req = pendingUsers.get(email);

        if (data == null || req == null) {
            throw new RuntimeException("Invalid request");
        }

        // expire check
        if (System.currentTimeMillis() > data.expiryTime) {
            otpStorage.remove(email);
            pendingUsers.remove(email);
            throw new RuntimeException("OTP expired");
        }

        // otp check
        if (!data.otp.equals(otp)) {
            throw new RuntimeException("Wrong OTP");
        }

        // USER SAVE NOW
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setMajor(req.getMajor());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        User savedUser = userRepository.save(user);

// DEFAULT PROFILE CREATE
        UserProfile profile = new UserProfile();
        profile.setUser(savedUser);

        profile.setBio(
                "Undergraduate student passionate about software development, web technologies, and AI applications."
        );

        profile.setBatchYear("2025");

        profileRepository.save(profile);

        otpStorage.remove(email);
        pendingUsers.remove(email);

// AUTO LOGIN TOKEN
        String token = jwtUtil.generateToken(
                savedUser.getEmail()
        );

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail()
        );
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
        message.setText("Your new OTP is: " + otp + " (valid 5 minutes)");

        mailSender.send(message);

        return "New OTP sent successfully";
    }




}


