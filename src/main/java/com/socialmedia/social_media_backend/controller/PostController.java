package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.dto.PostResponse;
import com.socialmedia.social_media_backend.entity.Post;
import com.socialmedia.social_media_backend.repository.CommentRepository;
import com.socialmedia.social_media_backend.repository.LikeRepository;
import com.socialmedia.social_media_backend.repository.PostRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public PostController(
            PostRepository postRepository,
            LikeRepository likeRepository,
            CommentRepository commentRepository) {

        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    // Create Post
    @PostMapping
    public Post createPost(
            @RequestBody Post post,
            Authentication authentication) {

        String userEmail = authentication.getName();

        post.setUserEmail(userEmail);

        return postRepository.save(post);
    }

    // Get All Posts with counts
    @GetMapping
    public List<PostResponse> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get Post By ID with counts
    @GetMapping("/{id}")
    public PostResponse getPostById(
            @PathVariable Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        return convertToResponse(post);
    }

    // Update Post
    @PutMapping("/{id}")
    public Post updatePost(
            @PathVariable Long id,
            @RequestBody Post post,
            Authentication authentication) {

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        String loggedInEmail = authentication.getName();

        if (!existingPost.getUserEmail().equals(loggedInEmail)) {
            throw new RuntimeException(
                    "You are not allowed to update this post");
        }

        existingPost.setContent(post.getContent());
        existingPost.setImageUrl(post.getImageUrl());

        return postRepository.save(existingPost);
    }

    // Delete Post
    @DeleteMapping("/{id}")
    public String deletePost(
            @PathVariable Long id,
            Authentication authentication) {

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        String loggedInEmail = authentication.getName();

        if (!existingPost.getUserEmail().equals(loggedInEmail)) {
            throw new RuntimeException(
                    "You are not allowed to delete this post");
        }

        postRepository.delete(existingPost);

        return "Post deleted successfully";
    }

    // Convert Post to PostResponse
    private PostResponse convertToResponse(Post post) {

        long likesCount =
                likeRepository.findByPostId(post.getId()).size();

        long commentsCount =
                commentRepository.findByPostId(post.getId()).size();

        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getImageUrl(),
                post.getUserEmail(),
                post.getCreatedAt(),
                likesCount,
                commentsCount
        );
    }
}