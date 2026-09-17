package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Follow;
import com.socialmedia.social_media_backend.entity.Post;
import com.socialmedia.social_media_backend.repository.FollowRepository;
import com.socialmedia.social_media_backend.repository.PostRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    public FeedController(
            PostRepository postRepository,
            FollowRepository followRepository) {

        this.postRepository = postRepository;
        this.followRepository = followRepository;
    }

    // Get feed posts from followed users + own posts
    @GetMapping
    public List<Post> getFeed(Authentication authentication) {

        String loggedInEmail = authentication.getName();

        // Get users that the logged-in user follows
        List<Follow> following =
                followRepository.findByFollowerEmail(loggedInEmail);

        List<String> emails = new ArrayList<>();

        // Add own email
        emails.add(loggedInEmail);

        // Add followed users
        for (Follow follow : following) {
            emails.add(follow.getFollowingEmail());
        }

        // Get posts
        List<Post> feedPosts = new ArrayList<>();

        for (String email : emails) {
            feedPosts.addAll(
                    postRepository.findByUserEmail(email)
            );
        }

        // Newest posts first
       feedPosts.sort(
        Comparator.comparing(Post::getCreatedAt).reversed()
        );

        return feedPosts;
    }

    // Get total post count
    @GetMapping("/count")
    public long getPostCount() {
        return postRepository.count();
    }

    // Get posts by user email
    @GetMapping("/user")
    public List<Post> getPostsByUser(
            @RequestParam String email) {

        return postRepository.findByUserEmail(email);
    }
}