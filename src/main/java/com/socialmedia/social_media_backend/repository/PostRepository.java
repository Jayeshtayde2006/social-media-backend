package com.socialmedia.social_media_backend.repository;

import com.socialmedia.social_media_backend.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserEmail(String userEmail);

    List<Post> findAllByOrderByIdDesc();
}