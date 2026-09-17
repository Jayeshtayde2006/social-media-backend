package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Like;
import com.socialmedia.social_media_backend.repository.LikeRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeRepository likeRepository;

    public LikeController(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // Add Like
    @PostMapping
    public Like addLike(
            @RequestBody Like like,
            Authentication authentication) {

        String userEmail = authentication.getName();

        if (likeRepository
                .findByPostIdAndUserEmail(
                        like.getPostId(),
                        userEmail)
                .isPresent()) {

            throw new RuntimeException(
                    "You have already liked this post");
        }

        like.setUserEmail(userEmail);

        return likeRepository.save(like);
    }

    // Get All Likes
    @GetMapping
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    // Get Like By ID
    @GetMapping("/{id}")
    public Like getLikeById(@PathVariable Long id) {

        return likeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Like not found"));
    }

    // Delete Like By Like ID
    @DeleteMapping("/{id}")
    public String deleteLike(
            @PathVariable Long id,
            Authentication authentication) {

        Like like = likeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Like not found"));

        String loggedInEmail = authentication.getName();

        if (!like.getUserEmail().equals(loggedInEmail)) {
            throw new RuntimeException(
                    "You are not allowed to delete this like");
        }

        likeRepository.delete(like);

        return "Like deleted successfully";
    }

    // Unlike Post By Post ID
    @DeleteMapping("/post/{postId}")
    public String unlikePost(
            @PathVariable Long postId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        Like like = likeRepository
                .findByPostIdAndUserEmail(
                        postId,
                        userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "You have not liked this post"));

        likeRepository.deleteById(like.getId());

        return "Post unliked successfully";
    }
}