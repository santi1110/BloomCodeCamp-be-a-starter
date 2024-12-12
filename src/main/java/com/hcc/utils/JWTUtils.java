package com.hcc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // Generate token method without static
    public static String generateToken(UserDetails userDetails, String jwtSecret, long jwtExpirationMs) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Set the username as the subject of the token
                .setIssuedAt(new Date()) // Set the issued time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Set expiration time
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Sign with the secret key
                .compact(); // Generate the JWT token
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check token expiration
    private boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration).before(new Date());
    }

    // Get claims from token
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Get all claims from the token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}
