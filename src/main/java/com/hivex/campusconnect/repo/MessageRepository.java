package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository
        extends JpaRepository<Message, Long> {

    List<Message> findByReceiverId(
            Long receiverId);

    List<Message> findBySenderIdAndReceiverId(
            Long senderId,
            Long receiverId
    );

    List<Message> findByReceiverIdAndSenderId(
            Long receiverId,
            Long senderId
    );

    @Query("""
            SELECT m FROM Message m
            WHERE
            (m.sender.id = :user1
            AND m.receiver.id = :user2)

            OR

            (m.sender.id = :user2
            AND m.receiver.id = :user1)

            ORDER BY m.sentAt ASC
            """)
    List<Message> getConversation(
            Long user1,
            Long user2);


    @Query("""
       SELECT m FROM Message m
       WHERE m.sender.id = :userId
       OR m.receiver.id = :userId
       ORDER BY m.sentAt DESC
       """)
    List<Message> findAllUserMessages(
            Long userId);


    @Query("""
       SELECT COUNT(m)
       FROM Message m
       WHERE m.receiver.id = :receiverId
       AND m.sender.id = :senderId
       AND m.readStatus = false
       """)
    Long countUnreadMessages(
            Long receiverId,
            Long senderId);


    @Modifying
    @Transactional
    @Query("""
        UPDATE Message m
        SET m.readStatus = true
        WHERE m.receiver.id = :receiverId
        AND m.sender.id = :senderId
        AND m.readStatus = false
        """)
    void markConversationAsRead(
            Long receiverId,
            Long senderId
    );
}