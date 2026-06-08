package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.UserProfileRequest;
import com.hivex.campusconnect.dto.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse createOrUpdateProfile(Long userId, UserProfileRequest request);

    UserProfileResponse getProfile(Long userId);

    String getProfileImage (Long userId);

    String getProfileName (Long userId);

    String getProfileEmail(Long userId);
}