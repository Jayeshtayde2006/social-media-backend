package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Post;
import com.socialmedia.social_media_backend.repository.PostRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
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

    // Get All Posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get Post By ID
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {

        return postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));
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
}