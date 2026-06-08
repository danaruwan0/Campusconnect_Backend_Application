package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.UserProfileRequest;
import com.hivex.campusconnect.dto.UserProfileResponse;
import com.hivex.campusconnect.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin
public class UserProfileController {

    private final UserProfileService profileService;

    @PostMapping("/{userId}")
    public UserProfileResponse createOrUpdate(
            @PathVariable Long userId,
            @RequestBody UserProfileRequest request) {

        return profileService.createOrUpdateProfile(userId, request);
    }

    @GetMapping("/{userId}")
    public UserProfileResponse getProfile(@PathVariable Long userId) {
        return profileService.getProfile(userId);
    }

    @GetMapping("/{userId}/profile-image")
    public String getProfileImage(@PathVariable Long userId) {
        return profileService.getProfileImage(userId);
    }


    @GetMapping("/{userId}/name")
    public String getName(@PathVariable Long userId) {
        return profileService.getProfileName(userId);
    }

    @GetMapping("/{userId}/email")
    public String getEmail(@PathVariable Long userId) {
        return profileService.getProfileEmail(userId);
    }
}