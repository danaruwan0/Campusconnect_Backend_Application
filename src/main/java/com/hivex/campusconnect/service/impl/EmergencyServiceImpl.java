package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.dto.Emergency.EmergencyRequest;
import com.hivex.campusconnect.dto.Emergency.EmergencyResponse;
import com.hivex.campusconnect.entity.EmergencyAlert;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.entity.UserProfile;
import com.hivex.campusconnect.repo.EmergencyRepository;
import com.hivex.campusconnect.repo.UserProfileRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.CloudinaryService;
import com.hivex.campusconnect.service.EmergencyService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


import com.hivex.campusconnect.service.NotificationService;

@Service
@RequiredArgsConstructor
public class EmergencyServiceImpl
        implements EmergencyService {

    private final EmergencyRepository emergencyRepository;

    private final UserRepository userRepository;

    private final UserProfileRepository profileRepository;

    private final CloudinaryService cloudinaryService;


    private final NotificationService notificationService;

    @Override
    public EmergencyResponse create(

            Long userId,

            EmergencyRequest request,

            MultipartFile image,

            MultipartFile video

    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        EmergencyAlert alert =
                new EmergencyAlert();

        alert.setTitle(request.getTitle());

        alert.setDescription(request.getDescription());

        alert.setLatitude(request.getLatitude());

        alert.setLongitude(request.getLongitude());

        alert.setLocationName(request.getLocationName());

        alert.setCreatedAt(LocalDateTime.now());

        alert.setCreatedBy(user);

        alert.setActive(true);

        // Upload Image

        if (image != null && !image.isEmpty()) {

            String imageUrl =
                    cloudinaryService.uploadFile(image);

            alert.setImageUrl(imageUrl);

        }

        // Upload Video

        if (video != null && !video.isEmpty()) {

            String videoUrl =
                    cloudinaryService.uploadFile(video);

            alert.setVideoUrl(videoUrl);

        }

        emergencyRepository.save(alert);

        notificationService.createEmergencyNotifications(alert);

        return mapToResponse(alert);

    }

    private EmergencyResponse mapToResponse(
            EmergencyAlert alert) {

        EmergencyResponse response =
                new EmergencyResponse();

        response.setId(alert.getId());

        response.setTitle(alert.getTitle());

        response.setDescription(alert.getDescription());

        response.setLatitude(alert.getLatitude());

        response.setLongitude(alert.getLongitude());

        response.setLocationName(alert.getLocationName());

        response.setImageUrl(alert.getImageUrl());

        response.setVideoUrl(alert.getVideoUrl());

        response.setCreatedAt(alert.getCreatedAt());

        response.setSenderName(
                alert.getCreatedBy().getFullName());

        UserProfile profile =
                profileRepository.findByUserId(
                        alert.getCreatedBy().getId()
                ).orElse(null);

        if (profile != null) {

            response.setSenderProfileImage(
                    profile.getProfileImage()
            );

        }

        return response;

    }

    @Override
    public List<EmergencyResponse> getAll() {

        return emergencyRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public EmergencyResponse get(Long id) {

        EmergencyAlert alert =
                emergencyRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Emergency not found"
                                ));

        return mapToResponse(alert);

    }

}