package com.hivex.campusconnect.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketMessage {

    private Long senderId;

    private Long receiverId;

    private String senderName;


    private String senderImage;   // <-- add

    private String content;

}