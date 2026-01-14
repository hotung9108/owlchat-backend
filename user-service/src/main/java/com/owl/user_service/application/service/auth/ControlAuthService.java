package com.owl.user_service.application.service.auth;

// import com.owl.user_service.domain.entity.RefreshToken;
import com.owl.user_service.domain.service.RefreshTokenService;
import com.owl.user_service.domain.service.AuthService;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import com.owl.user_service.presentation.dto.request.auth.AuthRequest;
import com.owl.user_service.presentation.dto.request.auth.AuthResponse;

@Service
public class ControlAuthService {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public ControlAuthService(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse login(AuthRequest authRequest) {
        String accessToken = authService.generateAccessToken(authRequest.getUserName(), Map.of("role", "USER"));
        String refreshToken = authService.generateRefreshToken(authRequest.getUserName());
        refreshTokenService.createRefreshToken(authRequest.getUserName());
        return new AuthResponse(accessToken, refreshToken);
    }

    public Optional<AuthResponse> refreshAccessToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .filter(token -> !refreshTokenService.isTokenExpired(token))
                .map(token -> {
                    String accessToken = authService.generateAccessToken(token.getUsername(), Map.of("role", "USER"));
                    return new AuthResponse(accessToken, refreshToken);
                });
    }

    public void logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);
    }
}
