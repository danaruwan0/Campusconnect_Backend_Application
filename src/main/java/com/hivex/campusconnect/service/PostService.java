package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.post.PostResponse;
import com.hivex.campusconnect.entity.Post;
import com.hivex.campusconnect.entity.PostComment;
import com.hivex.campusconnect.entity.PostReaction;
import org.springframework.web.multipart.MultipartFile;

import com.hivex.campusconnect.dto.CommentResponse;

import com.hivex.campusconnect.dto.CommentResponse;

import java.util.List;

public interface PostService {

    Post createPost(
            Long userId,
            String title,
            String content,
            MultipartFile image
    );

    List<PostResponse> getFeed();

    PostComment addComment(
            Long postId,
            Long userId,
            String comment
    );

//    List<PostComment> getComments(Long postId);

    List<CommentResponse> getComments(Long postId);

    PostReaction reactPost(
            Long postId,
            Long userId,
            String reactionType
    );

    void removeReaction(
            Long postId,
            Long userId
    );


    List<PostResponse> getUserPosts(Long userId);


}