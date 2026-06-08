package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository
        extends JpaRepository<PostComment, Long> {

    long countByPostId(Long postId);
}