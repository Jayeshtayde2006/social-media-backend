package com.socialmedia.social_media_backend.dto;

import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private String content;
    private String imageUrl;
    private String userEmail;
    private LocalDateTime createdAt;
    private long likesCount;
    private long commentsCount;

    public PostResponse(
            Long id,
            String content,
            String imageUrl,
            String userEmail,
            LocalDateTime createdAt,
            long likesCount,
            long commentsCount) {

        this.id = id;
        this.content = content;
        this.imageUrl = imageUrl;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }
}