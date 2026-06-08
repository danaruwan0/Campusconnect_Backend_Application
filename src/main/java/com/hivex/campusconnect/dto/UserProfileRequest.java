package com.hivex.campusconnect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequest {

    private String profileImage;
    private String coverImage;
    private String bio;
    private String university;
    private String batchYear;
    private String skills;
    private String linkedinUrl;
    private String githubUrl;
    private String website;
    private String phone;
    private String location;
}