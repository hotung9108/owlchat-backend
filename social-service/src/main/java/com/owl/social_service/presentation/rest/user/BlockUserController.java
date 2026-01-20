package com.owl.social_service.presentation.rest.user;

import java.time.Instant;

import com.owl.social_service.application.user.GetBlockUserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/block")
public class BlockUserController {

    private final GetBlockUserServices getBlockUserServices;
    public BlockUserController(GetBlockUserServices getBlockUserServices) {
        this.getBlockUserServices = getBlockUserServices;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?>  getBlockById(
        @RequestHeader String requesterId,
        @PathVariable String id
    ) 
    {
        try {
            return ResponseEntity.ok().body(getBlockUserServices.getBlockById(requesterId, id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/blocked")
    public ResponseEntity<?> getUserBlocked(
        @RequestHeader String requesterId,
        @PathVariable String userId,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false) Instant createdDateStart,
        @RequestParam(required = false) Instant createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(getBlockUserServices.getUserBlocked(requesterId, page, size, ascSort, createdDateStart, createdDateEnd));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
