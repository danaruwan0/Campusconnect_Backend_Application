package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.UserProfileRequest;
import com.hivex.campusconnect.dto.UserProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {

    UserProfileResponse createOrUpdateProfile(Long userId, UserProfileRequest request);

    UserProfileResponse getProfile(Long userId);

    String getProfileImage(Long userId);

    String getProfileName(Long userId);

    String getProfileEmail(Long userId);

    // NEW
    String uploadProfileImage(Long userId, MultipartFile file);

    String uploadCoverImage(Long userId, MultipartFile file);
}