package com.hivex.campusconnect.dto.auth;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String otp;
}