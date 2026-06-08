package com.hivex.campusconnect.dto.auth;

import lombok.Data;

@Data
public class PendingUser {
    private RegisterRequest request;
    private long expiryTime;
}