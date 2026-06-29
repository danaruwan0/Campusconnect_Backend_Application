package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository
        extends JpaRepository<PostComment, Long> {

    long countByPostId(Long postId);

    List<PostComment> findByPostIdOrderByCreatedAtDesc(Long postId);
}