package com.owl.social_service.presentation.rest.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import com.owl.social_service.application.user.ControlFriendshipUserServices;
import com.owl.social_service.application.user.GetFriendshipUserServies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/friendship")
public class FriendshipUserController {

    private final ControlFriendshipUserServices controlFriendshipUserServices;

    private final GetFriendshipUserServies getFriendshipUserServies;

    public FriendshipUserController(GetFriendshipUserServies getFriendshipUserServies,
            ControlFriendshipUserServices controlFriendshipUserServices) {
        this.getFriendshipUserServies = getFriendshipUserServies;
        this.controlFriendshipUserServices = controlFriendshipUserServices;
    }

    @GetMapping("")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getFriendships(
            @RequestHeader(value = "X-Account-Id", required = false) String accountId,
            @RequestParam(required = false) String requesterId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean ascSort,
            // @RequestParam(required = false) String keywords,
            @RequestParam(required = false) Instant createdDateStart,
            @RequestParam(required = false) Instant createdDateEnd) {
        try {
            String finalRequesterId = (requesterId != null) ? requesterId : accountId;
            return ResponseEntity.ok().body(getFriendshipUserServies.getFriendshipsOfUser(finalRequesterId, page, size,
                    ascSort, createdDateStart, createdDateEnd));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getFriendshipById(
            @RequestHeader(value = "X-Account-Id", required = false) String accountId,
            @RequestParam(required = false) String requesterId,
            @PathVariable String id) {
        try {
            String finalRequesterId = (requesterId != null) ? requesterId : accountId;
            return ResponseEntity.ok().body(getFriendshipUserServies.getFrienshipById(finalRequesterId, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getFriendshipWithUser(
            @RequestHeader(value = "X-Account-Id", required = false) String accountId,
            @RequestParam(required = false) String requesterId,
            @PathVariable String userId) {
        try {
            String finalRequesterId = (requesterId != null) ? requesterId : accountId;
            return ResponseEntity.ok().body(getFriendshipUserServies.getFriendshipWithUser(finalRequesterId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> deleteFriendship(
            @RequestHeader(value = "X-Account-Id", required = false) String accountId,
            @RequestParam(required = false) String requesterId,
            @PathVariable String id) {
        try {
            String finalRequesterId = (requesterId != null) ? requesterId : accountId;
            controlFriendshipUserServices.deleteFriendship(finalRequesterId, id);
            return ResponseEntity.ok().body("Friendship deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
