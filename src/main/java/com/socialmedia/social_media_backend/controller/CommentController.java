package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Comment;
import com.socialmedia.social_media_backend.repository.CommentRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Add Comment
    @PostMapping
    public Comment addComment(
            @RequestBody Comment comment,
            Authentication authentication) {

        String userEmail = authentication.getName();

        comment.setUserEmail(userEmail);

        return commentRepository.save(comment);
    }

    // Get All Comments
    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Get Comment By ID
    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {

        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Comment not found"));
    }

    // Update Comment
    @PutMapping("/{id}")
    public Comment updateComment(
            @PathVariable Long id,
            @RequestBody Comment comment,
            Authentication authentication) {

        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Comment not found"));

        String loggedInEmail = authentication.getName();

        if (!existingComment.getUserEmail().equals(loggedInEmail)) {
            throw new RuntimeException(
                    "You are not allowed to update this comment");
        }

        // Update only content
        existingComment.setContent(comment.getContent());

        return commentRepository.save(existingComment);
    }

    // Delete Comment
    @DeleteMapping("/{id}")
    public String deleteComment(
            @PathVariable Long id,
            Authentication authentication) {

        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Comment not found"));

        String loggedInEmail = authentication.getName();

        if (!existingComment.getUserEmail().equals(loggedInEmail)) {
            throw new RuntimeException(
                    "You are not allowed to delete this comment");
        }

        commentRepository.delete(existingComment);

        return "Comment deleted successfully";
    }
}