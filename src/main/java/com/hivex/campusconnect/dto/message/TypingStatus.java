package com.hivex.campusconnect.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypingStatus {

    private Long senderId;

    private Long receiverId;

    private boolean typing;
}