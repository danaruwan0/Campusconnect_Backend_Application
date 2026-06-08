package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.auth.AuthResponse;
import com.hivex.campusconnect.dto.auth.LoginRequest;
import com.hivex.campusconnect.dto.auth.RegisterRequest;
import com.hivex.campusconnect.entity.User;

public interface UserService {

    User register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}