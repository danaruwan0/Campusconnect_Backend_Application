package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.message.ChatListResponse;
import com.hivex.campusconnect.dto.message.MessageRequest;
import com.hivex.campusconnect.dto.message.MessageResponse;
import com.hivex.campusconnect.dto.message.SocketMessage;

import java.util.List;

public interface MessageService {

    MessageResponse sendMessage(
            MessageRequest request);

    List<MessageResponse> getMessages(
            Long userId);

    SocketMessage saveSocketMessage(
            SocketMessage message);

    List<MessageResponse> getConversation(
            Long user1,
            Long user2);

//    void markAsRead(
//            Long messageId);

    void markConversationAsRead(
            Long receiverId,
            Long senderId
    );

    List<ChatListResponse> getChatList(
            Long userId);

//    void deleteMessage(Long messageId);

    void deleteMessage(
            Long messageId,
            Long userId
    );


}