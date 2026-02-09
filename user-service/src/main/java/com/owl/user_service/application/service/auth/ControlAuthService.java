package com.owl.user_service.application.service.auth;

// import com.owl.user_service.domain.entity.RefreshToken;
import com.owl.user_service.domain.service.RefreshTokenService;
import com.owl.user_service.infrastructure.utils.JwtUtil;
import com.owl.user_service.persistence.jpa.entity.Account;
import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import com.owl.user_service.persistence.jpa.entity.UserProfile;
import com.owl.user_service.persistence.jpa.repository.AccountJpaRepository;
import com.owl.user_service.persistence.jpa.repository.UserProfileJpaRepository;
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
    private final AccountJpaRepository accountJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    public ControlAuthService(AuthService authService, RefreshTokenService refreshTokenService,
            AccountJpaRepository accountJpaRepository, UserProfileJpaRepository userProfileJpaRepository) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.accountJpaRepository = accountJpaRepository;
        this.userProfileJpaRepository = userProfileJpaRepository;

    }

    public AuthResponse login(AuthRequest authRequest) {
        Account account = accountJpaRepository.findByUsername(authRequest.getUsername());
        if (account == null) {
            UserProfile userProfile = userProfileJpaRepository.findByEmail(authRequest.getUsername());
            
            if (userProfile == null)
                throw new IllegalArgumentException("Account not found");
            
            account = userProfile.getAccount();

            if (account == null)
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
        if (account.getStatus() == false) {
            throw new IllegalArgumentException("Your account have been ban");
        }
        String accessToken = authService.generateAccessToken(
                account.getUsername(),
                account.getRole().toString(),
                account.getId());
        String refreshToken = authService.generateRefreshToken(account.getId());
        refreshTokenService.createRefreshToken(account.getUsername(), account.getId(), refreshToken);

        return new AuthResponse(accessToken, refreshToken, account.getRole().toString(), account.getStatus());
    }

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
