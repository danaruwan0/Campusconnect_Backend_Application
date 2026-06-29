package com.hivex.campusconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private Long userId;
    private String fullName;
    private String comment;
    private LocalDateTime createdAt;

    private String profileImage; // ADD




}