package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Follow;
import com.socialmedia.social_media_backend.repository.FollowRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowRepository followRepository;

    public FollowController(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    // Follow a user
    @PostMapping("/{followingEmail}")
    public Follow followUser(
            @PathVariable String followingEmail,
            Authentication authentication) {

        String followerEmail = authentication.getName();

        if (followerEmail.equals(followingEmail)) {
            throw new RuntimeException("You cannot follow yourself");
        }

        if (followRepository
                .findByFollowerEmailAndFollowingEmail(
                        followerEmail, followingEmail)
                .isPresent()) {

            throw new RuntimeException("Already following this user");
        }

        Follow follow = new Follow();
        follow.setFollowerEmail(followerEmail);
        follow.setFollowingEmail(followingEmail);

        return followRepository.save(follow);
    }

    // Unfollow a user
    @DeleteMapping("/{followingEmail}")
    public String unfollowUser(
            @PathVariable String followingEmail,
            Authentication authentication) {

        String followerEmail = authentication.getName();

        Follow follow = followRepository
                .findByFollowerEmailAndFollowingEmail(
                        followerEmail, followingEmail)
                .orElseThrow(() ->
                        new RuntimeException("You are not following this user"));

        followRepository.delete(follow);

        return "Unfollowed successfully";
    }

    // Get following list
    @GetMapping("/following")
    public List<Follow> getFollowing(Authentication authentication) {

        String followerEmail = authentication.getName();

        return followRepository.findByFollowerEmail(followerEmail);
    }

    // Get followers list
    @GetMapping("/followers")
    public List<Follow> getFollowers(Authentication authentication) {

        String followingEmail = authentication.getName();

        return followRepository.findByFollowingEmail(followingEmail);
    }
}