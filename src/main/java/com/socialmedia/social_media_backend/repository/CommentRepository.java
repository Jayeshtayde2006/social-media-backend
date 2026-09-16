package com.socialmedia.social_media_backend.repository;

import com.socialmedia.social_media_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}