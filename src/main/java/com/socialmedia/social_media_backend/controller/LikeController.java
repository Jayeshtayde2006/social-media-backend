package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Like;
import com.socialmedia.social_media_backend.repository.LikeRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

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

    Optional<Like> existingLike =
            likeRepository.findByPostIdAndUserEmail(
                    like.getPostId(),
                    userEmail
            );

    if (existingLike.isPresent()) {
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

    // Remove Like
  @DeleteMapping("/{id}")
public String deleteLike(
        @PathVariable Long id,
        Authentication authentication) {

    Like existingLike = likeRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Like not found"));

    String loggedInEmail = authentication.getName();

    if (!existingLike.getUserEmail().equals(loggedInEmail)) {
        throw new RuntimeException(
                "You are not allowed to remove this like");
    }

    likeRepository.delete(existingLike);

    return "Like removed successfully";
}
}