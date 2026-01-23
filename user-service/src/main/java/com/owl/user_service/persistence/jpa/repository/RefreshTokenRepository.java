package com.owl.user_service.persistence.jpa.repository;

import com.owl.user_service.persistence.jpa.entity.RefreshToken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    // Optional<RefreshToken> findByToken(String token);
    // void deleteByToken(String token);
    
    // findByToken
    Optional<RefreshToken> findByToken(String token);

    //deleteByToken
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.token = :token")
    void deleteByToken(@Param("token") String token);

    //revokeToken
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.token = :token")
    void revokeToken(@Param("token") String token);

    //existsValidToken
    @Query("SELECT CASE WHEN COUNT(rt) > 0 THEN true ELSE false END FROM RefreshToken rt WHERE rt.token = :token AND rt.revoked = false AND rt.expiredDate > CURRENT_TIMESTAMP")
    boolean existsValidToken(@Param("token") String token);
}
