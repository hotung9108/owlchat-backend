package com.owl.user_service.domain.service;

import com.owl.user_service.infrastructure.utils.JwtUtil;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {

    public String generateAccessToken(String username, Map<String, Object> claims) {
        return JwtUtil.generateAccessToken(username, claims);
    }

    public String generateRefreshToken(String username) {
        return JwtUtil.generateRefreshToken(username);
    }

    public boolean validateToken(String token) {
        return JwtUtil.validateToken(token);
    }

    public String extractUsername(String token) {
        return JwtUtil.extractUsername(token);
    }
}
