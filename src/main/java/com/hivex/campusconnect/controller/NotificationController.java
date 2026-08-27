package com.hivex.campusconnect.controller;


import com.hivex.campusconnect.dto.notification.NotificationResponse;
import com.hivex.campusconnect.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Get All Notifications of User
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                notificationService.getNotifications(userId)
        );

    }

    /**
     * Get Unread Notification Count
     */
    @GetMapping("/{userId}/count")
    public ResponseEntity<Long> getUnreadCount(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                notificationService.getUnreadCount(userId)
        );

    }

    /**
     * Mark Notification As Read
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long notificationId
    ) {

        notificationService.markAsRead(notificationId);

        return ResponseEntity.ok("Notification marked as read.");

    }

}