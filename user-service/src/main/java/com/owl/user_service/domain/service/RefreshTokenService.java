package com.owl.user_service.domain.service;

import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import com.owl.user_service.persistence.jpa.repository.RefreshTokenRepository;
import com.owl.user_service.infrastructure.utils.JwtUtil;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // public RefreshToken createRefreshToken(String username, String accountId) {
    // RefreshToken refreshToken = new RefreshToken();
    // refreshToken.setToken(UUID.randomUUID().toString());
    // refreshToken.setUsername(username);
    // refreshToken.setAccountId(accountId);
    // refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 *
    // 60 * 24 * 7)); // 7 days
    // return refreshTokenRepository.save(refreshToken);
    // }
    // public RefreshToken createRefreshToken(String username, String accountId,
    // String token) {
    // RefreshToken refreshToken = new RefreshToken();
    // refreshToken.setToken(token);
    // refreshToken.setUsername(username);
    // refreshToken.setAccountId(accountId);
    // refreshToken.setExpiredDate(LocalDateTime.now().plusDays(7)); // Token hết
    // hạn sau 7 ngày
    // refreshToken.setRevoked(false);
    // refreshToken.setCreatedDate(LocalDateTime.now());
    // refreshToken.setUpdatedDate(LocalDateTime.now());
    // return refreshTokenRepository.save(refreshToken);
    // }
    public RefreshToken createRefreshToken(String username, String accountId, String token) {
        // Generate refresh token using JwtUtil
        // String refreshToken = JwtUtil.generateRefreshToken(accountId);

        // Save the token in the database
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setUsername(username);
        tokenEntity.setToken(token);
        tokenEntity.setAccountId(accountId);
        tokenEntity.setExpiredDate(LocalDateTime.now().plusDays(7));
        tokenEntity.setRevoked(false);
        tokenEntity.setCreatedDate(LocalDateTime.now());
        tokenEntity.setUpdatedDate(LocalDateTime.now());
        return refreshTokenRepository.save(tokenEntity);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiredDate().isBefore(LocalDateTime.now());
    }

    @Transactional
    public void revokeToken(String token) {
        refreshTokenRepository.revokeToken(token);
    }

    public boolean isTokenValid(String token) {
        return refreshTokenRepository.existsValidToken(token);
    }

    public List<RefreshToken> getAllRefreshTokens() {
        return refreshTokenRepository.findAll();
    }

}
