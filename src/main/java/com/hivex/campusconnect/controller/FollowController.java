package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.hivex.campusconnect.dto.follow.FollowUserResponse;
import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followerId}/{followingId}")
    public String followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        return followService.followUser(
                followerId,
                followingId
        );
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public String unfollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        return followService.unfollowUser(
                followerId,
                followingId
        );
    }


    @GetMapping("/{followerId}/{followingId}/status")
    public boolean isFollowing(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        return followService.isFollowing(
                followerId,
                followingId
        );
    }


    @GetMapping("/{userId}/followers")
    public List<FollowUserResponse> getFollowers(
            @PathVariable Long userId) {

        return followService.getFollowers(userId);
    }

    @GetMapping("/{userId}/following")
    public List<FollowUserResponse> getFollowing(
            @PathVariable Long userId) {

        return followService.getFollowing(userId);
    }

    @GetMapping("/{userId}/suggestions")
    public List<FollowUserResponse> getSuggestions(
            @PathVariable Long userId) {

        return followService.getSuggestions(userId);
    }




    @GetMapping("/{userId}/followers/count")
    public Long getFollowerCount(@PathVariable Long userId){
        return followService.getFollowerCount(userId);
    }

    @GetMapping("/{userId}/following/count")
    public Long getFollowingCount(@PathVariable Long userId){
        return followService.getFollowingCount(userId);
    }
}