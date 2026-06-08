package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.PostShare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostShareRepository
        extends JpaRepository<PostShare, Long> {

    long countByPostId(Long postId);
}