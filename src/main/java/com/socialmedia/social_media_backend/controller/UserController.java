package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.User;
import com.socialmedia.social_media_backend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.socialmedia.social_media_backend.dto.LoginResponse;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE USER
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable("id") Long id,
            @RequestBody User user) {

        return userService.updateUser(id, user);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return "User deleted successfully";
    }

    @PostMapping("/login")
public LoginResponse login(@RequestBody User user) {

    return userService.loginUser(
            user.getEmail(),
            user.getPassword()
    );
}

@PostMapping("/logout")
public String logout(Authentication authentication) {

    String email = authentication.getName();

    return "Logout successful for " + email;
}
}