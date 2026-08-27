package com.hivex.campusconnect.dto.notification;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {

    private Long id;

    private String senderName;

    private String senderProfileImage;

    private String title;

    private String message;

    private String type;

    private Long referenceId;

    private boolean readStatus;

    private LocalDateTime createdAt;

}