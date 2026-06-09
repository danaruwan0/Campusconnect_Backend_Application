package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.dto.UserProfileRequest;
import com.hivex.campusconnect.dto.UserProfileResponse;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.entity.UserProfile;
import com.hivex.campusconnect.repo.UserProfileRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hivex.campusconnect.service.CloudinaryService;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public

class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;

    private final CloudinaryService cloudinaryService;

    @Override
    public UserProfileResponse createOrUpdateProfile(Long userId, UserProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElse(new UserProfile());

        profile.setUser(user);

//        profile.setProfileImage(request.getProfileImage());
//        profile.setCoverImage(request.getCoverImage());


        profile.setBio(request.getBio());
        profile.setUniversity(request.getUniversity());
        profile.setBatchYear(request.getBatchYear());
        profile.setSkills(request.getSkills());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setWebsite(request.getWebsite());
        profile.setPhone(request.getPhone());
        profile.setLocation(request.getLocation());

        profileRepository.save(profile);

        return mapToResponse(profile, user);
    }

    @Override
    public UserProfileResponse getProfile(Long userId) {

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        User user = profile.getUser();

        return mapToResponse(profile, user);
    }



    @Override
    public String getProfileImage(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return profile.getProfileImage();
//        return null;
    }

    @Override
    public String getProfileName(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFullName();
    }

    @Override
    public String getProfileEmail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getEmail();
    }


    private UserProfileResponse mapToResponse(UserProfile profile, User user) {

        UserProfileResponse res = new UserProfileResponse();

        res.setId(profile.getId());
        res.setFullName(user.getFullName());
        res.setEmail(user.getEmail());

        res.setProfileImage(profile.getProfileImage());
        res.setCoverImage(profile.getCoverImage());
        res.setBio(profile.getBio());
        res.setUniversity(profile.getUniversity());
        res.setBatchYear(profile.getBatchYear());
        res.setSkills(profile.getSkills());
        res.setLinkedinUrl(profile.getLinkedinUrl());
        res.setGithubUrl(profile.getGithubUrl());
        res.setWebsite(profile.getWebsite());
        res.setPhone(profile.getPhone());
        res.setLocation(profile.getLocation());

        return res;
    }

    @Override
    public String uploadProfileImage(Long userId, MultipartFile file) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> {

                    UserProfile newProfile = new UserProfile();
                    newProfile.setUser(user);

                    return newProfile;
                });

        String imageUrl = cloudinaryService.uploadFile(file);

        profile.setProfileImage(imageUrl);

        profileRepository.save(profile);

        return imageUrl;
    }


    @Override
    public String uploadCoverImage(Long userId, MultipartFile file) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> {

                    UserProfile newProfile = new UserProfile();
                    newProfile.setUser(user);

                    return newProfile;
                });

        String imageUrl = cloudinaryService.uploadFile(file);

        profile.setCoverImage(imageUrl);

        profileRepository.save(profile);

        return imageUrl;
    }



}