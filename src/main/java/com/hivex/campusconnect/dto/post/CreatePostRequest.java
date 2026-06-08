package com.hivex.campusconnect.dto.post;

import lombok.Data;

@Data
public class CreatePostRequest {

    private Long userId;

    private String title;

    private String content;
}