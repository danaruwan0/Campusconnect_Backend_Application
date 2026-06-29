package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.entity.UserFollow;
import com.hivex.campusconnect.repo.UserFollowRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hivex.campusconnect.dto.follow.FollowUserResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl
        implements FollowService {

    private final UserRepository userRepository;
    private final UserFollowRepository followRepository;

    @Override
    public String followUser(
            Long followerId,
            Long followingId) {

        if (followerId.equals(followingId)) {
            throw new RuntimeException(
                    "Cannot follow yourself"
            );
        }

        if (followRepository
                .findByFollowerIdAndFollowingId(
                        followerId,
                        followingId
                )
                .isPresent()) {

            return "Already following";
        }

        User follower =
                userRepository.findById(followerId)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        User following =
                userRepository.findById(followingId)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        UserFollow follow = UserFollow.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(follow);

        return "Followed successfully";
    }

    @Override
    public String unfollowUser(
            Long followerId,
            Long followingId) {

        followRepository.deleteByFollowerIdAndFollowingId(
                followerId,
                followingId
        );

        return "Unfollowed successfully";
    }

    @Override
    public long getFollowersCount(Long userId) {

        return followRepository
                .countByFollowingId(userId);
    }

//    @Override
//    public long getFollowingCount(Long userId) {
//
//        return followRepository
//                .countByFollowerId(userId);
//    }

    @Override
    public boolean isFollowing(
            Long followerId,
            Long followingId) {

        return followRepository
                .findByFollowerIdAndFollowingId(
                        followerId,
                        followingId
                )
                .isPresent();
    }


    @Override
    public List<FollowUserResponse> getFollowers(
            Long userId) {

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

    @Override
    public List<FollowUserResponse> getFollowing(
            Long userId) {

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

    @Override
    public List<FollowUserResponse> getSuggestions(
            Long userId) {

        return userRepository
                .getSuggestedUsers(userId)
                .stream()
                .map(user ->
                        FollowUserResponse.builder()
                                .userId(user.getId())
                                .fullName(user.getFullName())
                                .email(user.getEmail())
                                .build()
                )
                .toList();
    }

//    @Override
//    public Long getFollowerCount(Long userId) {
//        return 0;
//    }


    @Override
    public Long getFollowerCount(Long userId) {
        return followRepository.countByFollowingId(userId);
    }

    @Override
    public Long getFollowingCount(Long userId) {
        return followRepository.countByFollowerId(userId);
    }
}