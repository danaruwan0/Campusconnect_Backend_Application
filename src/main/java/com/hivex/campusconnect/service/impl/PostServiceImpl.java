package com.hivex.campusconnect.service.impl;

import com.hivex.campusconnect.dto.post.PostResponse;
import com.hivex.campusconnect.entity.Post;
import com.hivex.campusconnect.entity.PostComment;
import com.hivex.campusconnect.entity.PostReaction;
import com.hivex.campusconnect.entity.User;
import com.hivex.campusconnect.repo.PostCommentRepository;
import com.hivex.campusconnect.repo.PostReactionRepository;
import com.hivex.campusconnect.repo.PostRepository;
import com.hivex.campusconnect.repo.UserRepository;
import com.hivex.campusconnect.service.CloudinaryService;
import com.hivex.campusconnect.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hivex.campusconnect.dto.CommentResponse;

import java.util.List;

import com.hivex.campusconnect.entity.UserProfile;
import com.hivex.campusconnect.repo.UserProfileRepository;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;


    //new line
    private final UserProfileRepository userProfileRepository;

    private final PostCommentRepository commentRepository;
    private final PostReactionRepository reactionRepository;

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

            mediaUrl = cloudinaryService.uploadFile(image);

            String contentType = image.getContentType();

            if (contentType != null &&
                    contentType.startsWith("video")) {

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
                .map(post -> {

                    String postProfileImage = userProfileRepository
                            .findByUserId(post.getUser().getId())
                            .map(UserProfile::getProfileImage)
                            .orElse(null);

                    List<CommentResponse> comments = commentRepository
                            .findByPostIdOrderByCreatedAtDesc(post.getId())
                            .stream()
                            .map(comment -> {

                                String commentProfileImage = userProfileRepository
                                        .findByUserId(comment.getUser().getId())
                                        .map(UserProfile::getProfileImage)
                                        .orElse(null);

                                return CommentResponse.builder()
                                        .id(comment.getId())
                                        .userId(comment.getUser().getId())
                                        .fullName(comment.getUser().getFullName())
                                        .profileImage(commentProfileImage)
                                        .comment(comment.getComment())
                                        .createdAt(comment.getCreatedAt())
                                        .build();

                            })
                            .toList();

                    return PostResponse.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .mediaUrl(post.getMediaUrl())
                            .mediaType(post.getMediaType())
                            .createdAt(post.getCreatedAt())

                            .userId(post.getUser().getId())
                            .fullName(post.getUser().getFullName())
                            .profileImage(postProfileImage)

                            .reactionCount(
                                    reactionRepository.countByPostId(post.getId())
                            )
                            .commentCount(
                                    commentRepository.countByPostId(post.getId())
                            )
                            .shareCount(0)

                            .comments(comments)

                            .build();

                })
                .toList();
    }

    @Override
    public PostComment addComment(
            Long postId,
            Long userId,
            String comment) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        PostComment postComment = PostComment.builder()
                .comment(comment)
                .post(post)
                .user(user)
                .build();

        return commentRepository.save(postComment);
    }

//    @Override
//    public List<PostComment> getComments(Long postId) {
//
//        return commentRepository
//                .findByPostIdOrderByCreatedAtDesc(postId);
//    }



    @Override
    public List<CommentResponse> getComments(Long postId) {

        return commentRepository
                .findByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map(comment -> {

                    String profileImage = userProfileRepository
                            .findByUserId(comment.getUser().getId())
                            .map(UserProfile::getProfileImage)
                            .orElse(null);

                    return CommentResponse.builder()
                            .id(comment.getId())
                            .userId(comment.getUser().getId())
                            .fullName(comment.getUser().getFullName())
                            .profileImage(profileImage)
                            .comment(comment.getComment())
                            .createdAt(comment.getCreatedAt())
                            .build();
                })
                .toList();
    }


    @Override
    public PostReaction reactPost(
            Long postId,
            Long userId,
            String reactionType) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        PostReaction reaction = reactionRepository
                .findByPostIdAndUserId(postId, userId)
                .orElse(new PostReaction());

        reaction.setPost(post);
        reaction.setUser(user);
        reaction.setReactionType(reactionType);

        return reactionRepository.save(reaction);
    }

    @Override
    public void removeReaction(
            Long postId,
            Long userId) {

        reactionRepository
                .deleteByPostIdAndUserId(
                        postId,
                        userId
                );
    }

//    @Override
//    public List<PostResponse> getUserPosts(Long userId) {
//        return List.of();
//    }

    @Override
    public List<PostResponse> getUserPosts(Long userId) {

        return postRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(post -> {

                    String postProfileImage = userProfileRepository
                            .findByUserId(post.getUser().getId())
                            .map(UserProfile::getProfileImage)
                            .orElse(null);

                    List<CommentResponse> comments = commentRepository
                            .findByPostIdOrderByCreatedAtDesc(post.getId())
                            .stream()
                            .map(comment -> {

                                String commentProfileImage = userProfileRepository
                                        .findByUserId(comment.getUser().getId())
                                        .map(UserProfile::getProfileImage)
                                        .orElse(null);

                                return CommentResponse.builder()
                                        .id(comment.getId())
                                        .userId(comment.getUser().getId())
                                        .fullName(comment.getUser().getFullName())
                                        .profileImage(commentProfileImage)
                                        .comment(comment.getComment())
                                        .createdAt(comment.getCreatedAt())
                                        .build();
                            })
                            .toList();

                    return PostResponse.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .mediaUrl(post.getMediaUrl())
                            .mediaType(post.getMediaType())
                            .createdAt(post.getCreatedAt())

                            .userId(post.getUser().getId())
                            .fullName(post.getUser().getFullName())
                            .profileImage(postProfileImage)

                            .reactionCount(
                                    reactionRepository.countByPostId(post.getId())
                            )
                            .commentCount(
                                    commentRepository.countByPostId(post.getId())
                            )
                            .shareCount(0)

                            .comments(comments)

                            .build();
                })
                .toList();
    }
}