package com.hivex.campusconnect.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who receives notification
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    // User who triggered notification
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    private String title;

    @Column(length = 1000)
    private String message;

    // EMERGENCY, FOLLOW, MESSAGE, LIKE...
    private String type;

    // Emergency ID / Post ID / User ID ...
    private Long referenceId;

    private boolean readStatus;

    private LocalDateTime createdAt;
}