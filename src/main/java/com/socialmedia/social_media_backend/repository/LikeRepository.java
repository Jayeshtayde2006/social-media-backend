package com.socialmedia.social_media_backend.repository;

import com.socialmedia.social_media_backend.entity.Like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByPostIdAndUserEmail(
            Long postId,
            String userEmail
    );
}