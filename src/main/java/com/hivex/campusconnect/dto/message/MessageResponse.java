package com.hivex.campusconnect.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponse {

    private Long id;

    private Long senderId;
    private String senderName;

    private Long receiverId;
    private String receiverName;

    private String content;

    private LocalDateTime sentAt;

    private boolean readStatus;


    private String senderImage;

    private String receiverImage;

    //new add
    private boolean deletedBySender;

    private boolean deletedByReceiver;

    private boolean deletedForEveryone;



}