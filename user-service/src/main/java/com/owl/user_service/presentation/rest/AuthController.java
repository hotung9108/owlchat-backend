package com.owl.user_service.presentation.rest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.owl.user_service.application.service.auth.ControlAuthService;
import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import com.owl.user_service.presentation.dto.request.auth.AuthRequest;
import com.owl.user_service.presentation.dto.request.auth.AuthResponse;
import com.owl.user_service.presentation.dto.request.auth.RefreshTokenRequest;

import io.swagger.v3.oas.annotations.Operation;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ControlAuthService controlAuthService;

    public AuthController(ControlAuthService controlAuthService) {
        this.controlAuthService = controlAuthService;
    }
    @Operation(summary = "Login", security = {})
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse authResponse = controlAuthService.login(authRequest);
            return ResponseEntity.ok(authResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        try {
            return controlAuthService.refreshAccessToken(refreshToken)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(401).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        // controlAuthService.logout(refreshToken);
        try {
            controlAuthService.logout(refreshToken);
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }

        // return ResponseEntity.ok("Logged out successfully");
    }

    // @GetMapping
    // public List<RefreshToken> getAllRefreshTokens() {
    //     return controlAuthService.getAllRefreshTokens();
    // }
}
