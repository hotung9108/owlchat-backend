package com.owl.user_service.presentation.rest;

import org.springframework.web.bind.annotation.*;


import org.springframework.http.ResponseEntity;

import com.owl.user_service.application.service.auth.ControlAuthService;
import com.owl.user_service.application.service.user_profile.ControlUserProfileServices;
import com.owl.user_service.presentation.dto.request.auth.AuthRequest;
import com.owl.user_service.presentation.dto.request.auth.AuthResponse;
import com.owl.user_service.presentation.dto.request.auth.RefreshTokenRequest;
import com.owl.user_service.presentation.dto.request.auth.SignUpRequest;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(
    origins = "http://localhost:8080",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
    allowCredentials = "true"
)
public class AuthController {

    private final ControlAuthService controlAuthService;
    private final ControlUserProfileServices controlUserProfileServices;

    public AuthController(ControlAuthService controlAuthService, ControlUserProfileServices controlUserProfileServices) {
        this.controlAuthService = controlAuthService;
        this.controlUserProfileServices = controlUserProfileServices;
    }
    // @Operation(summary = "Login", security = {})
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

    @PostMapping("/signup")
    public ResponseEntity<?> Login(@RequestBody SignUpRequest request) {
        try {
            return ResponseEntity.ok(controlUserProfileServices.SignUp(request));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    // @GetMapping
    // public List<RefreshToken> getAllRefreshTokens() {
    //     return controlAuthService.getAllRefreshTokens();
    // }
}
