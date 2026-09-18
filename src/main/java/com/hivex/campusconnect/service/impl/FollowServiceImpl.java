package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.dto.follow.FollowUserResponse;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.entity.UserFollow;
import com.hivex.campusconnect.repo.UserFollowRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.FollowService;
import com.hivex.campusconnect.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl
        implements FollowService {


    private final UserRepository userRepository;

    private final UserFollowRepository followRepository;

    private final NotificationService notificationService;


    /*
     * =========================================================
     * FOLLOW USER
     * =========================================================
     */

    @Override
    public String followUser(
            Long followerId,
            Long followingId
    ) {

        /*
         * Cannot follow yourself
         */

        if (followerId.equals(followingId)) {

            throw new RuntimeException(
                    "Cannot follow yourself"
            );
        }


        /*
         * Check already following
         */

        if (followRepository
                .findByFollowerIdAndFollowingId(
                        followerId,
                        followingId
                )
                .isPresent()) {

            return "Already following";
        }


        /*
         * Find follower
         */

        User follower =
                userRepository.findById(followerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        /*
         * Find following user
         */

        User following =
                userRepository.findById(followingId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        /*
         * Create follow relationship
         */

        UserFollow follow =
                UserFollow.builder()
                        .follower(follower)
                        .following(following)
                        .build();


        followRepository.save(follow);


        /*
         * Create follow notification
         */

        notificationService.createFollowNotification(
                followerId,
                followingId
        );


        return "Followed successfully";
    }


    /*
     * =========================================================
     * UNFOLLOW USER
     * =========================================================
     */

    @Override
    public String unfollowUser(
            Long followerId,
            Long followingId
    ) {

        UserFollow follow =
                followRepository
                        .findByFollowerIdAndFollowingId(
                                followerId,
                                followingId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Follow relationship not found"
                                )
                        );


        followRepository.delete(follow);


        /*
         * Create unfollow notification
         */

        notificationService.createUnfollowNotification(
                followerId,
                followingId
        );


        return "Unfollowed successfully";
    }


    /*
     * =========================================================
     * GET FOLLOWERS COUNT
     * =========================================================
     */

    @Override
    public long getFollowersCount(Long userId) {

        return followRepository
                .countByFollowingId(userId);
    }


    /*
     * =========================================================
     * CHECK FOLLOWING STATUS
     * =========================================================
     */

    @Override
    public boolean isFollowing(
            Long followerId,
            Long followingId
    ) {

        return followRepository
                .findByFollowerIdAndFollowingId(
                        followerId,
                        followingId
                )
                .isPresent();
    }


    /*
     * =========================================================
     * GET FOLLOWERS
     * =========================================================
     */

    @Override
    public List<FollowUserResponse> getFollowers(
            Long userId
    ) {

        return followRepository
                .findByFollowingId(userId)
                .stream()
                .map(follow ->
                        FollowUserResponse.builder()
                                .userId(
                                        follow.getFollower().getId()
                                )
                                .fullName(
                                        follow.getFollower().getFullName()
                                )
                                .email(
                                        follow.getFollower().getEmail()
                                )
                                .build()
                )
                .toList();
    }


    /*
     * =========================================================
     * GET FOLLOWING
     * =========================================================
     */

    @Override
    public List<FollowUserResponse> getFollowing(
            Long userId
    ) {

        return followRepository
                .findByFollowerId(userId)
                .stream()
                .map(follow ->
                        FollowUserResponse.builder()
                                .userId(
                                        follow.getFollowing().getId()
                                )
                                .fullName(
                                        follow.getFollowing().getFullName()
                                )
                                .email(
                                        follow.getFollowing().getEmail()
                                )
                                .build()
                )
                .toList();
    }


    /*
     * =========================================================
     * GET SUGGESTIONS
     *
     * Profile image and major are already fetched
     * from User + UserProfile by UserRepository.
     *
     * No extra mapping is required here.
     * =========================================================
     */

    @Override
    public List<FollowUserResponse> getSuggestions(
            Long userId
    ) {

        return userRepository
                .getSuggestedUsers(userId);
    }


    /*
     * =========================================================
     * GET FOLLOWER COUNT
     * =========================================================
     */

    @Override
    public Long getFollowerCount(Long userId) {

        return followRepository
                .countByFollowingId(userId);
    }


    /*
     * =========================================================
     * GET FOLLOWING COUNT
     * =========================================================
     */

    @Override
    public Long getFollowingCount(Long userId) {

        return followRepository
                .countByFollowerId(userId);
    }
}