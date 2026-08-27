package com.hivex.campusconnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="emergency_alerts")
public class EmergencyAlert {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length=3000)
    private String description;

    private String imageUrl;

    private String videoUrl;

    private Double latitude;

    private Double longitude;

    private String locationName;

    private LocalDateTime createdAt;

    private boolean active;

    @ManyToOne
    @JoinColumn(name="created_by")
    private User createdBy;

}