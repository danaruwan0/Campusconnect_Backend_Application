package com.hivex.campusconnect.dto.post;

import lombok.Data;

@Data
public class CommentRequest {

    private Long userId;
    private String comment;
}