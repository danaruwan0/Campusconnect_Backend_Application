package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.notification.NotificationResponse;
import com.hivex.campusconnect.entity.EmergencyAlert;

import java.util.List;

public interface NotificationService {

    void createEmergencyNotifications(EmergencyAlert alert);

    List<NotificationResponse> getNotifications(Long userId);

    long getUnreadCount(Long userId);

    void markAsRead(Long notificationId);

}