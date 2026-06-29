package com.hivex.campusconnect.dto.post;

import lombok.Data;

@Data
public class ReactionRequest {

    private Long userId;
    private String reactionType;
}