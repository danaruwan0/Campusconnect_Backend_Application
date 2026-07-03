package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.message.OnlineStatus;
import com.hivex.campusconnect.dto.message.SocketMessage;
import com.hivex.campusconnect.service.MessageService;
import com.hivex.campusconnect.service.OnlineUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.hivex.campusconnect.dto.message.TypingStatus;

import com.hivex.campusconnect.dto.message.SeenStatus;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final MessageService messageService;

    private final OnlineUsers onlineUsers;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    public void sendMessage(SocketMessage message) {

        // Save to database and enrich sender details
        SocketMessage saved =
                messageService.saveSocketMessage(message);

        // Send to sender
        messagingTemplate.convertAndSend(
                "/topic/messages/" + saved.getSenderId(),
                saved
        );

        // Send to receiver
        messagingTemplate.convertAndSend(
                "/topic/messages/" + saved.getReceiverId(),
                saved
        );
    }

    @MessageMapping("/online")
    public void online(OnlineStatus status) {

        onlineUsers.userOnline(status.getUserId());

        status.setOnline(true);

        messagingTemplate.convertAndSend(
                "/topic/online",
                status
        );
    }

    @MessageMapping("/offline")
    public void offline(OnlineStatus status) {

        onlineUsers.userOffline(status.getUserId());

        status.setOnline(false);

        messagingTemplate.convertAndSend(
                "/topic/online",
                status
        );
    }


    @MessageMapping("/typing")
    public void typing(TypingStatus status) {

        System.out.println("Typing Event Received");
        System.out.println(status.getSenderId());
        System.out.println(status.getReceiverId());


        messagingTemplate.convertAndSend(
                "/topic/typing/" + status.getReceiverId(),
                status
        );

    }

    @MessageMapping("/stopTyping")
    public void stopTyping(TypingStatus status) {

        status.setTyping(false);

        messagingTemplate.convertAndSend(
                "/topic/typing/" + status.getReceiverId(),
                status
        );

    }

    @MessageMapping("/seen")
    public void seen(SeenStatus status) {

        messagingTemplate.convertAndSend(

                "/topic/seen/" + status.getSenderId(),

                status

        );

    }

}