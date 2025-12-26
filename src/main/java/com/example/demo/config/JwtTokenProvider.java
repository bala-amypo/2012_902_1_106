package com.example.demo.config;

import com.example.demo.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long expirationMillis;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, 
                           @Value("${jwt.expiration}") long expirationMillis) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(Long userId, String email, Role role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", role.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public Long extractUserId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }
}