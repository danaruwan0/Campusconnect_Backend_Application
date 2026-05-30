package com.hivex.campusconnect.controller;
import com.hivex.campusconnect.dto.RegisterRequest;

import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

//    @GetMapping("/login")

}