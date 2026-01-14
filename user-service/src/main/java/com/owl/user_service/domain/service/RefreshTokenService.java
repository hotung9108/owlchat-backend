package com.owl.user_service.domain.service;

import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import com.owl.user_service.persistence.jpa.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUsername(username);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)); // 7 days
        return refreshTokenRepository.save(refreshToken);
    }
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().before(new Date());
    }
}
