package com.owl.api_gateway.utils;
// import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
// import java.util.Map;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtUtil {

    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // Replace with a secure key

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    // Validate Token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract Username from Token
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                // .getSubject();
                .get("username", String.class);
    }

    public static Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    // Extract Role from Token
    public static String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // Extract AccountID from Token
    public static String extractAccountID(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
