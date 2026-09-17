package com.socialmedia.social_media_backend.repository;

import com.socialmedia.social_media_backend.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerEmailAndFollowingEmail(
            String followerEmail,
            String followingEmail
    );

    List<Follow> findByFollowerEmail(String followerEmail);

    List<Follow> findByFollowingEmail(String followingEmail);
}