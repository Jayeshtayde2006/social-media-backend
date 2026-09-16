package com.socialmedia.social_media_backend.service;

import com.socialmedia.social_media_backend.entity.User;
import com.socialmedia.social_media_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.socialmedia.social_media_backend.dto.LoginResponse;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // CREATE USER
  public User createUser(User user) {

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("Email already registered");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("USER");

    return userRepository.save(user);
}

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET USER BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + id));
    }

    // UPDATE USER
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + id));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());

        // Encrypt new password
        existingUser.setPassword(
                passwordEncoder.encode(updatedUser.getPassword())
        );

        return userRepository.save(existingUser);
    }

    // DELETE USER
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException(
                    "User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    // LOGIN USER
    public LoginResponse loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException(
                    "Invalid email or password");
        }

       

        String token = jwtService.generateToken(
        user.getEmail(),
        user.getRole()

        
);

    return new LoginResponse(
        "Login successful",
        token,
        user.getEmail()
     );
    }
}