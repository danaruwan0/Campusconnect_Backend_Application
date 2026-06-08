package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.post.CreatePostRequest;
import com.hivex.campusconnect.dto.post.PostResponse;
import com.hivex.campusconnect.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Post createPost(
            Long userId,
            String title,
            String content,
            MultipartFile image
    );

    List<PostResponse> getFeed();
}