package com.hivex.campusconnect.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatListResponse {

    private Long userId;

    private String fullName;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    private Long unreadCount;

//    new add
    private String profileImage;

    private boolean online;
}