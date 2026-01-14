package com.owl.user_service.presentation.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.owl.user_service.application.service.AuthService;
import com.owl.user_service.application.service.auth.ControlAuthService;
import com.owl.user_service.presentation.dto.request.auth.AuthRequest;
import com.owl.user_service.presentation.dto.request.auth.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ControlAuthService controlAuthService;

    public AuthController(ControlAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticate(authRequest.getUserName(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
