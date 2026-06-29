package com.hivex.campusconnect.dto.post;

import com.hivex.campusconnect.dto.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

//    private Long postId;
//
//    private String title;
//
//    private String content;
//
//    private String mediaUrl;
//
//    private String mediaType;
//
//    private LocalDateTime createdAt;
//
//    private Long userId;
//
//    private String fullName;
//
//    private long reactionCount;
//    private long commentCount;
//    private long shareCount;

    private Long postId;
    private String title;
    private String content;
    private String mediaUrl;
    private String mediaType;
    private LocalDateTime createdAt;

    private Long userId;
    private String fullName;

    private String profileImage; // ADD

    private long reactionCount;
    private long commentCount;
    private long shareCount;

    private List<CommentResponse> comments;

}