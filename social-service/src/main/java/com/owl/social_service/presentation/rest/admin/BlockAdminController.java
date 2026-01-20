package com.owl.social_service.presentation.rest.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import com.owl.social_service.application.admin.GetBlockAdminServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin/block")
public class BlockAdminController {

    private final GetBlockAdminServices getBlockAdminServices;
    public BlockAdminController(GetBlockAdminServices getBlockAdminServices) {
        this.getBlockAdminServices = getBlockAdminServices;
    }

    @GetMapping("")
    public ResponseEntity<?> getBlocks(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false) Instant createdDateStart,
        @RequestParam(required = false) Instant createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(getBlockAdminServices.getBlocks(page, size, ascSort, createdDateStart, createdDateEnd));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?>  getBlockById(
        @PathVariable String id
    ) 
    {
        try {
            return ResponseEntity.ok().body(getBlockAdminServices.getBlockById(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/blocked")
    public ResponseEntity<?> getUserBlocked(
        @PathVariable String userId,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false) Instant createdDateStart,
        @RequestParam(required = false) Instant createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(getBlockAdminServices.getUserBlocked(userId, page, size, ascSort, createdDateStart, createdDateEnd));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/blocker")
    public ResponseEntity<?> getUserBlocker(
        @PathVariable String userId,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false) Instant createdDateStart,
        @RequestParam(required = false) Instant createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(getBlockAdminServices.getUserBlocker(userId, page, size, ascSort, createdDateStart, createdDateEnd));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/blocker/{blockerId}/blocked/{blockedId}")
    public ResponseEntity<?> getUserBlockUser(
        @PathVariable String blockerId,
        @PathVariable String blockedId
    ) 
    {
        try {
            return ResponseEntity.ok().body(getBlockAdminServices.getUserBlockUser(blockerId, blockedId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
