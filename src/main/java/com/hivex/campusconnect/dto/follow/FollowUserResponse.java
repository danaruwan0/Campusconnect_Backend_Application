package com.hivex.campusconnect.dto.follow;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowUserResponse {

    private Long userId;

    private String fullName;

    private String email;

    private String major;

    private String profileImage;


    /*
     * Constructor for JPQL projection
     */
    public FollowUserResponse(
            Long userId,
            String fullName,
            String email,
            String major,
            String profileImage
    ) {

        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.major = major;
        this.profileImage = profileImage;
    }
}