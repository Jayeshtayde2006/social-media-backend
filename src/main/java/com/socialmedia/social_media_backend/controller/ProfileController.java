package com.socialmedia.social_media_backend.controller;

import com.socialmedia.social_media_backend.entity.Profile;
import com.socialmedia.social_media_backend.repository.ProfileRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // Create Profile
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Profile profile) {

        if (profileRepository.findByUserEmail(profile.getUserEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Profile already exists for this user");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profileRepository.save(profile));
    }

    // Get All Profiles
    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    // Get Profile By ID
    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable Long id) {

        return profileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));
    }

    // Update Profile
    @PutMapping("/{id}")
    public Profile updateProfile(
            @PathVariable Long id,
            @RequestBody Profile profile) {

        Profile existingProfile = profileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));

        existingProfile.setUserEmail(profile.getUserEmail());
        existingProfile.setBio(profile.getBio());
        existingProfile.setProfilePicture(profile.getProfilePicture());
        existingProfile.setLocation(profile.getLocation());
        existingProfile.setWebsite(profile.getWebsite());

        return profileRepository.save(existingProfile);
    }

    // Delete Profile
    @DeleteMapping("/{id}")
    public String deleteProfile(@PathVariable Long id) {

        profileRepository.deleteById(id);

        return "Profile deleted successfully";
    }

    // Get Profile By Email
    @GetMapping("/by-email")
    public Profile getProfileByUserEmail(
            @RequestParam String email) {

        return profileRepository.findByUserEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));
    }
}