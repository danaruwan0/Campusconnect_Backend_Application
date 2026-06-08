package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.dto.post.PostResponse;
import com.hivex.campusconnect.entity.Post;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.repo.PostRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.CloudinaryService;
import com.hivex.campusconnect.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Post createPost(
            Long userId,
            String title,
            String content,
            MultipartFile image
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String mediaUrl = null;
        String mediaType = null;

        if (image != null && !image.isEmpty()) {

            mediaUrl =
                    cloudinaryService.uploadFile(image);

            String contentType =
                    image.getContentType();

            if (contentType != null
                    && contentType.startsWith("video")) {

                mediaType = "VIDEO";

            } else {

                mediaType = "IMAGE";
            }
        }

        Post post = Post.builder()
                .title(title)
                .content(content)
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .user(user)
                .build();

        return postRepository.save(post);
    }

    @Override
    public List<PostResponse> getFeed() {

        return postRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> PostResponse.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .mediaUrl(post.getMediaUrl())
                        .mediaType(post.getMediaType())
                        .createdAt(post.getCreatedAt())
                        .userId(post.getUser().getId())
                        .fullName(post.getUser().getFullName())
                        .reactionCount(0)
                        .commentCount(0)
                        .shareCount(0)
                        .build())
                .toList();
    }
}