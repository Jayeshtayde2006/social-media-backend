package com.socialmedia.social_media_backend.repository;

import com.socialmedia.social_media_backend.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserEmail(String userEmail);
}