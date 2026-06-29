package com.hivex.campusconnect.dto.follow;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowUserResponse {

    private Long userId;
    private String fullName;
    private String email;
}