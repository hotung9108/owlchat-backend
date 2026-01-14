package com.owl.user_service.persistence.jpa.repository;

import com.owl.user_service.persistence.jpa.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}
