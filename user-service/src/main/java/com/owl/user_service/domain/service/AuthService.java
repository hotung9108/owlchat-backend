package com.owl.user_service.domain.service;

import com.owl.user_service.infrastructure.utils.JwtUtil;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
    public boolean verifyPassword(String rawPassword, String storedPassword) {
        return rawPassword.equals(storedPassword);
    }

    public String generateAccessToken(String username,String role, String accountId) {
        return JwtUtil.generateAccessToken(username,role, accountId);
    }

    public String generateRefreshToken(String accountId) {
        return JwtUtil.generateRefreshToken(accountId);
    }

    public boolean validateToken(String token) {
        return JwtUtil.validateToken(token);
    }

    public String extractUsername(String token) {
        return JwtUtil.extractUsername(token);
    }
}
