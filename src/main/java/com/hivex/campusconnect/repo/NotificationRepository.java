package com.hivex.campusconnect.repo;
import com.hivex.campusconnect.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);

    long countByReceiverIdAndReadStatusFalse(Long receiverId);

    //add dalate 26/8/27
    void deleteByIdAndReceiverId(
            Long notificationId,
            Long receiverId
    );



}