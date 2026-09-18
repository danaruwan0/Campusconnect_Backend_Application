package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.notification.NotificationResponse;
import com.hivex.campusconnect.entity.EmergencyAlert;

import java.util.List;

public interface NotificationService {

    void createEmergencyNotifications(
            EmergencyAlert alert
    );

    void createFollowNotification(
            Long followerId,
            Long followingId
    );

    List<NotificationResponse> getNotifications(
            Long userId
    );

    long getUnreadCount(
            Long userId
    );

    void markAsRead(
            Long notificationId
    );

    void deleteNotification(
            Long notificationId,
            Long userId
    );


    //un folow
    void createUnfollowNotification(
            Long followerId,
            Long followingId
    );
}