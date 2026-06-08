package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.post.CreatePostRequest;
import com.hivex.campusconnect.dto.post.PostResponse;
import com.hivex.campusconnect.entity.Post;
import com.hivex.campusconnect.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
}