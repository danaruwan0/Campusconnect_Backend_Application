package com.hivex.campusconnect.dto;


import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;
    private String email;
    private String major;
    private String password;
}