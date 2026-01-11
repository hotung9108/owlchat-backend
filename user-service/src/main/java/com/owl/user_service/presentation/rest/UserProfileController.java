package com.owl.user_service.presentation.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.user_service.application.service.user_profile.GetUserProfileService;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    private final GetUserProfileService getUserProfileService;

    public UserProfileController(GetUserProfileService getUserProfileService) {
        this.getUserProfileService = getUserProfileService;
    }

    @GetMapping("")
    public ResponseEntity getProfiles(
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page, 
        @RequestParam(required = false, defaultValue = "10") int size, 
        @RequestParam(required =  false, defaultValue = "0") int gender, 
        @RequestParam(required =  false, defaultValue = "") LocalDateTime dateOfBirthStart, 
        @RequestParam(required =  false, defaultValue = "") LocalDateTime dateOfBirthEnd, 
        @RequestParam(required =  false, defaultValue = "true") boolean ascSort
    ) 
    {
        try 
        {
            return ResponseEntity.ok(getUserProfileService.getUserProfiles(keywords, page, size, gender, dateOfBirthStart, dateOfBirthEnd, ascSort));    
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getProfileById(@PathVariable String id) {
        try 
        {
            return ResponseEntity.ok(getUserProfileService.getUserProfileById(id));    
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public String updateProfile(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @PatchMapping("path/{id}")
    public String updateAvatar(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }
}
