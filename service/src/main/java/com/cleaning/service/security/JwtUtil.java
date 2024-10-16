package com.cleaning.service.security;


import com.cleaning.service.exceptions.InvalidJwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-in-ms}")
    private long jwtExpirationInMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        try {
            key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid JWT secret key", e);
        }
    }

    /**
     * Genererar en JWT för en given användare.
     *
     * @param userDetails Användarens detaljer.
     * @return En JWT som en sträng.
     */
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getPublicId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Hämtar användarens publicId från JWT.
     *
     * @param token JWT-token.
     * @return Användarens publicId.
     */
    public String getPublicIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validerar JWT-token.
     *
     * @param authToken JWT-token.
     * @return {@code true} om token är giltig, annars {@code false}.
     * @throws InvalidJwtAuthenticationException Om token är ogiltig eller förfalskad.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new InvalidJwtAuthenticationException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new InvalidJwtAuthenticationException("Unsupported JWT token");
        } catch (MalformedJwtException ex) {
            throw new InvalidJwtAuthenticationException("Invalid JWT token");
        } catch (SignatureException ex) {
            throw new InvalidJwtAuthenticationException("Invalid JWT signature");
        } catch (IllegalArgumentException ex) {
            throw new InvalidJwtAuthenticationException("JWT claims string is empty.");
        }
    }
}