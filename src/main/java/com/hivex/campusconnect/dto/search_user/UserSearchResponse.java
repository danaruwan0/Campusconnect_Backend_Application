package com.hivex.campusconnect.dto.search_user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String major;
}

