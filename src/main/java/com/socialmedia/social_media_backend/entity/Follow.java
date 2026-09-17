package com.socialmedia.social_media_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "follows",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"followerEmail", "followingEmail"})
    }
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String followerEmail;

    private String followingEmail;

    public Follow() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFollowerEmail() {
        return followerEmail;
    }

    public void setFollowerEmail(String followerEmail) {
        this.followerEmail = followerEmail;
    }

    public String getFollowingEmail() {
        return followingEmail;
    }

    public void setFollowingEmail(String followingEmail) {
        this.followingEmail = followingEmail;
    }
}