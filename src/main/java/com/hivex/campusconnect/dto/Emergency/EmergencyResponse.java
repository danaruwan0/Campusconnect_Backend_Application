package com.hivex.campusconnect.dto.Emergency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyResponse {

    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private String videoUrl;

    private Double latitude;

    private Double longitude;

    private String locationName;

    private String senderName;

    private String senderProfileImage;

    private LocalDateTime createdAt;

}