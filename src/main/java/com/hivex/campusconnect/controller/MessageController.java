package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.message.ChatListResponse;
import com.hivex.campusconnect.dto.message.MessageRequest;
import com.hivex.campusconnect.dto.message.MessageResponse;
import com.hivex.campusconnect.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public MessageResponse sendMessage(
            @RequestBody MessageRequest request) {

        return messageService.sendMessage(request);
    }

    @GetMapping("/{userId}")
    public List<MessageResponse> getMessages(
            @PathVariable Long userId) {

        return messageService.getMessages(userId);
    }

    @GetMapping("/conversation/{user1}/{user2}")
    public List<MessageResponse>
    getConversation(

            @PathVariable Long user1,
            @PathVariable Long user2) {

        return messageService
                .getConversation(
                        user1,
                        user2);
    }

//    @PutMapping("/read/{messageId}")
//    public void markAsRead(
//            @PathVariable Long messageId) {
//
//        messageService.markAsRead(
//                messageId);
//    }


    @PutMapping("/read/{receiverId}/{senderId}")
    public void markConversationAsRead(

            @PathVariable Long receiverId,

            @PathVariable Long senderId

    ) {

        messageService.markConversationAsRead(

                receiverId,

                senderId

        );

    }

    @GetMapping("/chat-list/{userId}")
    public List<ChatListResponse> getChatList(
            @PathVariable Long userId) {

        return messageService
                .getChatList(userId);
    }

    @DeleteMapping("/{messageId}/{userId}")
    public String deleteMessage(

            @PathVariable Long messageId,

            @PathVariable Long userId) {

        messageService.deleteMessage(
                messageId,
                userId);

        return "Message deleted successfully";
    }


}