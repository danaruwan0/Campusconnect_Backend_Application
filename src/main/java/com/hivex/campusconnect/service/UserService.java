package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.RegisterRequest;
import com.hivex.campusconnect.entity.User;

public interface UserService {
    User register(RegisterRequest request);



}