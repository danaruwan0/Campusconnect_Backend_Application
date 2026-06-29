package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.follow.FollowUserResponse;

import java.util.List;

public interface FollowService {

    String followUser(
            Long followerId,
            Long followingId
    );

    String unfollowUser(
            Long followerId,
            Long followingId
    );

    long getFollowersCount(Long userId);



    boolean isFollowing(
            Long followerId,
            Long followingId
    );

    List<FollowUserResponse> getFollowers(
            Long userId
    );

    List<FollowUserResponse> getFollowing(
            Long userId
    );

    List<FollowUserResponse> getSuggestions(
            Long userId
    );


    Long getFollowerCount(Long userId);

    Long getFollowingCount(Long userId);

//    Long getFollowingCount(Long userId);
}