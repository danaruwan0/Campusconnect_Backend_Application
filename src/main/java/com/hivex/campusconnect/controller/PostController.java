package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.post.PostResponse;
import com.hivex.campusconnect.entity.Post;
import com.hivex.campusconnect.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.hivex.campusconnect.dto.post.CommentRequest;
import com.hivex.campusconnect.dto.post.ReactionRequest;
import com.hivex.campusconnect.entity.PostComment;
import com.hivex.campusconnect.entity.PostReaction;

//new add
import com.hivex.campusconnect.dto.CommentResponse;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Post> createPost(

            @RequestParam Long userId,

            @RequestParam(required = false)
            String title,

            @RequestParam(required = false)
            String content,

            @RequestParam(required = false)
            MultipartFile image
    ) {

        return ResponseEntity.ok(
                postService.createPost(
                        userId,
                        title,
                        content,
                        image
                )
        );
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed() {



        return ResponseEntity.ok(
                postService.getFeed()
        );
    }

//    only user all feeds get use   user prophile


    @PostMapping("/{postId}/comments")
    public ResponseEntity<PostComment> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request) {

        return ResponseEntity.ok(
                postService.addComment(
                        postId,
                        request.getUserId(),
                        request.getComment()
                )
        );
    }
//
//    @GetMapping("/{postId}/comments")
//    public ResponseEntity<List<PostComment>> getComments(
//            @PathVariable Long postId) {
//
//        return ResponseEntity.ok(
//                postService.getComments(postId)
//        );
//    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long postId) {

        return ResponseEntity.ok(
                postService.getComments(postId)
        );
    }

    @PostMapping("/{postId}/react")
    public ResponseEntity<PostReaction> reactPost(
            @PathVariable Long postId,
            @RequestBody ReactionRequest request) {

        return ResponseEntity.ok(
                postService.reactPost(
                        postId,
                        request.getUserId(),
                        request.getReactionType()
                )
        );
    }

    @DeleteMapping("/{postId}/react/{userId}")
    public ResponseEntity<String> removeReaction(
            @PathVariable Long postId,
            @PathVariable Long userId) {

        postService.removeReaction(
                postId,
                userId);

        return ResponseEntity.ok(
                "Reaction removed"
        );
    }




    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(
            @PathVariable Long userId){

        return ResponseEntity.ok(
                postService.getUserPosts(userId)
        );
    }

}