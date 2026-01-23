package com.owl.user_service.application.service.auth;

// import com.owl.user_service.domain.entity.RefreshToken;
import com.owl.user_service.domain.service.RefreshTokenService;
import com.owl.user_service.infrastructure.utils.JwtUtil;
import com.owl.user_service.persistence.jpa.entity.Account;
import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import com.owl.user_service.persistence.jpa.repository.AccountJpaRepository;
// import com.owl.user_service.domain.service.AccountServices;
import com.owl.user_service.domain.service.AuthService;

import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

// import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import com.owl.user_service.presentation.dto.request.auth.AuthRequest;
import com.owl.user_service.presentation.dto.request.auth.AuthResponse;

@Service
public class ControlAuthService {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    // private final AccountServices accountServices;
    private final AccountJpaRepository accountJpaRepository;

    public ControlAuthService(AuthService authService, RefreshTokenService refreshTokenService,
            AccountJpaRepository accountJpaRepository) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        // this.accountServices = accountServices;
        this.accountJpaRepository = accountJpaRepository;

    }

    public AuthResponse login(AuthRequest authRequest) {
        Account account = accountJpaRepository.findByUsername(authRequest.getUsername());
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        if (!authService.verifyPassword(authRequest.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (account.getRole() == null) {
            throw new IllegalArgumentException("Account role is null");
        }
        if (account.getId() == null) {
            throw new IllegalArgumentException("Account ID is null");
        }
        String accessToken = authService.generateAccessToken(
                account.getUsername(),
                account.getRole().toString(),
                account.getId());
        String refreshToken = authService.generateRefreshToken(account.getId());
        refreshTokenService.createRefreshToken(account.getUsername(), account.getId(), refreshToken);
        // String refreshToken = "";
        // String refreshToken =
        // authService.generateRefreshToken(authRequest.getUsername());
        return new AuthResponse(accessToken, refreshToken);
    }

    // public AuthResponse login(AuthRequest authRequest) {
    // Account account =
    // accountJpaRepository.findByUsername(authRequest.getUsername());
    // if (account == null) {
    // throw new IllegalArgumentException("Account not found");
    // }
    // if (!authService.verifyPassword(authRequest.getPassword(),
    // account.getPassword()))
    // ;

    // String accessToken =
    // authService.generateAccessToken(authRequest.getUsername(),
    // Map.of("role", account.getRole().toString()));
    // String refreshToken =
    // authService.generateRefreshToken(authRequest.getUsername());

    // // String refreshToken =
    // // authService.generateRefreshToken(authRequest.getUsername());
    // refreshTokenService.createRefreshToken(authRequest.getUsername(),
    // account.getId().toString(), refreshToken);
    // return new AuthResponse(accessToken, refreshToken);
    // }

    // public Optional<AuthResponse> refreshAccessToken(String refreshToken) {
    // return refreshTokenService.findByToken(refreshToken)
    // .filter(token -> !refreshTokenService.isTokenExpired(token))
    // .map(token -> {
    // String accessToken = authService.generateAccessToken(token.getUsername(),
    // Map.of("role", "USER"));
    // return new AuthResponse(accessToken, refreshToken);
    // });
    // }
    public Optional<AuthResponse> refreshAccessToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .filter(token -> !refreshTokenService.isTokenExpired(token))
                .map(token -> {
                    String accountId = JwtUtil.extractAccountID(refreshToken);
                    Account account = accountJpaRepository.findById(accountId)
                            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
                    String accessToken = authService.generateAccessToken(
                            account.getUsername(),
                            account.getRole().toString(),
                            account.getId());
                    return new AuthResponse(accessToken, refreshToken);
                });
    }

    public void logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);
    }

    public List<RefreshToken> getAllRefreshTokens() {
        return refreshTokenService.getAllRefreshTokens();
    }

}
