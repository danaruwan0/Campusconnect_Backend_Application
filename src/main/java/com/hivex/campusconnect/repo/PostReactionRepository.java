package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.PostReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostReactionRepository
        extends JpaRepository<PostReaction, Long> {

    long countByPostId(Long postId);

    Optional<PostReaction>
    findByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);
}