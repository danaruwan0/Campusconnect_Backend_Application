package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.dto.message.ChatListResponse;
import com.hivex.campusconnect.dto.message.MessageRequest;
import com.hivex.campusconnect.dto.message.MessageResponse;
import com.hivex.campusconnect.dto.message.SocketMessage;
import com.hivex.campusconnect.entity.Message;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.repo.MessageRepository;
import com.hivex.campusconnect.repo.UserProfileRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl
        implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private final UserProfileRepository profileRepository;


    @Override
    public MessageResponse sendMessage(
            MessageRequest request) {

        User sender =
                userRepository.findById(
                                request.getSenderId())
                        .orElseThrow();

        User receiver =
                userRepository.findById(
                                request.getReceiverId())
                        .orElseThrow();

        Message message = new Message();

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(request.getContent());
        message.setSentAt(LocalDateTime.now());
        message.setReadStatus(false);



        //new add


        messageRepository.save(message);

        return map(message);
    }

    @Override
    public SocketMessage saveSocketMessage(SocketMessage socketMessage) {

        User sender = userRepository.findById(socketMessage.getSenderId())
                .orElseThrow();

        User receiver = userRepository.findById(socketMessage.getReceiverId())
                .orElseThrow();

        Message message = new Message();

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(socketMessage.getContent());
        message.setSentAt(LocalDateTime.now());
        message.setReadStatus(false);

        messageRepository.save(message);

        socketMessage.setSenderName(sender.getFullName());

        String senderImage = profileRepository
                .findByUserId(sender.getId())
                .map(profile -> profile.getProfileImage())
                .orElse(null);

        socketMessage.setSenderImage(senderImage);

        return socketMessage;
    }

    @Override
    public List<MessageResponse> getMessages(
            Long userId) {

        return messageRepository
                .findByReceiverId(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    private MessageResponse map(Message message) {

        MessageResponse res = new MessageResponse();

        res.setId(message.getId());

        res.setSenderId(message.getSender().getId());
        res.setSenderName(message.getSender().getFullName());

        res.setReceiverId(message.getReceiver().getId());
        res.setReceiverName(message.getReceiver().getFullName());

        res.setContent(message.getContent());
        res.setSentAt(message.getSentAt());
        res.setReadStatus(message.isReadStatus());

        String senderImage = profileRepository
                .findByUserId(message.getSender().getId())
                .map(profile -> profile.getProfileImage())
                .orElse(null);

        String receiverImage = profileRepository
                .findByUserId(message.getReceiver().getId())
                .map(profile -> profile.getProfileImage())
                .orElse(null);

        res.setSenderImage(senderImage);
        res.setReceiverImage(receiverImage);

        // Delete status
        res.setDeletedBySender(message.isDeletedBySender());
        res.setDeletedByReceiver(message.isDeletedByReceiver());
        res.setDeletedForEveryone(message.isDeletedForEveryone());

        return res;
    }





    @Override
    public List<MessageResponse> getConversation(
            Long user1,
            Long user2) {

        return messageRepository
                .getConversation(
                        user1,
                        user2)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public void markConversationAsRead(
            Long receiverId,
            Long senderId
    ) {

        messageRepository.markConversationAsRead(
                receiverId,
                senderId
        );

    }

    @Override
    public List<ChatListResponse> getChatList(
            Long userId) {

        List<Message> messages =
                messageRepository
                        .findAllUserMessages(userId);

        Map<Long, ChatListResponse> chats =
                new LinkedHashMap<>();

        for (Message message : messages) {

            User otherUser;

            if (message.getSender().getId()
                    .equals(userId)) {

                otherUser =
                        message.getReceiver();

            } else {

                otherUser =
                        message.getSender();
            }

            if (!chats.containsKey(
                    otherUser.getId())) {

                ChatListResponse chat =
                        new ChatListResponse();

                chat.setUserId(
                        otherUser.getId());

                chat.setFullName(
                        otherUser.getFullName());

                chat.setLastMessage(
                        message.getContent());

                chat.setLastMessageTime(
                        message.getSentAt());

                //new add
                String profileImage = profileRepository
                        .findByUserId(otherUser.getId())
                        .map(profile -> profile.getProfileImage())
                        .orElse(null);

                chat.setProfileImage(profileImage);

                chat.setOnline(false);


                Long unread =
                        messageRepository
                                .countUnreadMessages(
                                        userId,
                                        otherUser.getId());

                chat.setUnreadCount(
                        unread);

                chats.put(
                        otherUser.getId(),
                        chat);
            }
        }

        return chats.values()
                .stream()
                .toList();
    }

    @Override
    public void deleteMessage(
            Long messageId,
            Long userId) {

        Message message =
                messageRepository
                        .findById(messageId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Message not found"));

        if (!message.getSender()
                .getId()
                .equals(userId)) {

            throw new RuntimeException(
                    "You can delete only your messages");
        }

        messageRepository.delete(message);
    }

    @Override
    public void deleteForMe(Long messageId, Long userId) {

        Message message =
                messageRepository.findById(messageId)
                        .orElseThrow();

        if(message.getSender().getId().equals(userId)){

            messageRepository.deleteForMeSender(messageId);

        }else{

            messageRepository.deleteForMeReceiver(messageId);

        }

    }

    @Override
    public void deleteForEveryone(
            Long messageId,
            Long userId) {

        Message message =
                messageRepository.findById(messageId)
                        .orElseThrow();

        if(!message.getSender().getId().equals(userId)){

            throw new RuntimeException(
                    "Only sender can delete for everyone");

        }

        messageRepository.deleteForEveryone(messageId);

    }
}