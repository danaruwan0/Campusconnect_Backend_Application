package com.hivex.campusconnect.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long postId;

    private String title;

    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    private Long userId;

    private String fullName;

    private int reactionCount;

    private int commentCount;

    private int shareCount;
}