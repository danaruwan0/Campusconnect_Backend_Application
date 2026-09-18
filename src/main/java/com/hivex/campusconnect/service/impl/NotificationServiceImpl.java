package com.hivex.campusconnect.service.impl;


import com.hivex.campusconnect.dto.notification.NotificationResponse;
import com.hivex.campusconnect.entity.EmergencyAlert;
import com.hivex.campusconnect.entity.Notification;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.entity.UserProfile;
import com.hivex.campusconnect.repo.NotificationRepository;
import com.hivex.campusconnect.repo.UserProfileRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final UserProfileRepository profileRepository;

    /**
     * Create Emergency Notification
     */
    @Override
    public void createEmergencyNotifications(EmergencyAlert alert) {

        List<User> users = userRepository.findAll();

        for (User receiver : users) {

            // Sender receives no notification
            if (receiver.getId().equals(alert.getCreatedBy().getId())) {
                continue;
            }

            Notification notification = new Notification();

            notification.setReceiver(receiver);

            notification.setSender(alert.getCreatedBy());

            notification.setTitle("Emergency Alert");

            notification.setMessage(alert.getTitle());

            notification.setType("EMERGENCY");

            notification.setReferenceId(alert.getId());

            notification.setReadStatus(false);

            notification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(notification);
        }

    }

    /**
     * Get Notifications
     */
    @Override
    public List<NotificationResponse> getNotifications(Long userId) {

        return notificationRepository
                .findByReceiverIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    /**
     * Unread Count
     */
    @Override
    public long getUnreadCount(Long userId) {

        return notificationRepository
                .countByReceiverIdAndReadStatusFalse(userId);

    }

    /**
     * Mark As Read
     */
    @Override
    public void markAsRead(Long notificationId) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found"));

        notification.setReadStatus(true);

        notificationRepository.save(notification);

    }

    /**
     * Entity -> DTO
     */
    private NotificationResponse mapToResponse(
            Notification notification
    ) {

        NotificationResponse response =
                new NotificationResponse();

        response.setId(notification.getId());

        response.setTitle(notification.getTitle());

        response.setMessage(notification.getMessage());

        response.setType(notification.getType());

        response.setReferenceId(notification.getReferenceId());

        response.setReadStatus(notification.isReadStatus());

        response.setCreatedAt(notification.getCreatedAt());

        if (notification.getSender() != null) {

            response.setSenderName(
                    notification.getSender().getFullName()
            );

            UserProfile profile =
                    profileRepository
                            .findByUserId(
                                    notification.getSender().getId()
                            )
                            .orElse(null);

            if (profile != null) {

                response.setSenderProfileImage(
                        profile.getProfileImage()
                );

            }

        }

        return response;

    }

    /**
     * Create Follow Notification
     */
    @Override
    public void createFollowNotification(
            Long followerId,
            Long followingId
    ) {

        User follower =
                userRepository.findById(followerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Follower user not found"
                                ));

        User following =
                userRepository.findById(followingId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Following user not found"
                                ));

        Notification notification =
                new Notification();

        // Person receiving notification
        notification.setReceiver(following);

        // Person who followed
        notification.setSender(follower);

        notification.setTitle("New Follower");

        notification.setMessage(
                follower.getFullName()
                        + " started following you"
        );

        notification.setType("FOLLOW");

        // Follower ID
        notification.setReferenceId(
                follower.getId()
        );

        notification.setReadStatus(false);

        notification.setCreatedAt(
                LocalDateTime.now()
        );

        notificationRepository.save(notification);
    }


    /**
     * Delete Notification
     */
    @Override
    public void deleteNotification(
            Long notificationId,
            Long userId
    ) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        // Security check
        if (!notification.getReceiver()
                .getId()
                .equals(userId)) {

            throw new RuntimeException(
                    "You cannot delete this notification"
            );
        }

        notificationRepository.delete(notification);
    }



    @Override
    public void createUnfollowNotification(
            Long followerId,
            Long followingId
    ) {

        User follower =
                userRepository.findById(followerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Follower user not found"
                                ));

        User following =
                userRepository.findById(followingId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Following user not found"
                                ));

        Notification notification =
                new Notification();

        // Person receiving notification
        notification.setReceiver(following);

        // Person who unfollowed
        notification.setSender(follower);

        notification.setTitle("Unfollowed You");

        notification.setMessage(
                follower.getFullName()
                        + " unfollowed you"
        );

        notification.setType("UNFOLLOW");

        notification.setReferenceId(
                follower.getId()
        );

        notification.setReadStatus(false);

        notification.setCreatedAt(
                LocalDateTime.now()
        );

        notificationRepository.save(notification);
    }





}