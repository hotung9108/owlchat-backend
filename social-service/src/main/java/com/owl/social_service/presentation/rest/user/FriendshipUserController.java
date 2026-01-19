package com.owl.social_service.presentation.rest.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/friendship")
public class FriendshipUserController {
    public FriendshipUserController() {}

    @GetMapping("")
    public ResponseEntity<?> getFriendships(
        @RequestHeader String requesterId,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false) Instant createdDateStart,
        @RequestParam(required = false) Instant createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFriendshipById(
        @RequestHeader String requesterId,
        @PathVariable String id
    ) 
    {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getFriendshipWithUser(
        @RequestHeader String requesterId,
        @PathVariable String userId
    ) 
    {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFriendship(
        @RequestHeader String requesterId,
        @PathVariable String id
    ) 
    {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
