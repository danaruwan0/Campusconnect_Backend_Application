package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowRepository
        extends JpaRepository<UserFollow, Long> {

    Optional<UserFollow>
    findByFollowerIdAndFollowingId(
            Long followerId,
            Long followingId
    );

    long countByFollowerId(Long followerId);

    long countByFollowingId(Long followingId);

    List<UserFollow> findByFollowerId(Long followerId);

    List<UserFollow> findByFollowingId(Long followingId);

    void deleteByFollowerIdAndFollowingId(
            Long followerId,
            Long followingId
    );


//
//    long countByFollowerId(Long followerId);
//
//    long countByFollowingId(Long followingId);




}