package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.auth.AuthResponse;
import com.hivex.campusconnect.dto.auth.LoginRequest;
import com.hivex.campusconnect.dto.auth.RegisterRequest;
import com.hivex.campusconnect.dto.search_user.UserSearchResponse;
import com.hivex.campusconnect.entity.User;

import java.util.List;

public interface UserService {

    User register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    List<UserSearchResponse> searchUsers(
            String keyword
    );
}