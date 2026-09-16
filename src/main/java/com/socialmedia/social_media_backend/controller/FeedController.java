package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Post;
import com.socialmedia.social_media_backend.repository.PostRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final PostRepository postRepository;

    public FeedController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Get all posts
    @GetMapping
    public List<Post> getFeed() {
        return postRepository.findAll();
    }

    // Get total post count
    @GetMapping("/count")
    public long getPostCount() {
        return postRepository.count();
    }

    // Get posts by user email
    @GetMapping("/user")
    public List<Post> getPostsByUser(@RequestParam String email) {
        return postRepository.findByUserEmail(email);
    }
}