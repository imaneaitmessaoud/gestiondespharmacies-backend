package com.pharmactrl.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // en millisecondes

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

   public String generateToken(UserDetails userDetails, long expiration) {
    List<String> roles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("roles", roles) // ⬅ ajoute les rôles ici
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
}

public String generateAccessToken(UserDetails userDetails) {
    return generateToken(userDetails, 1000 * 60 * 15); // 15 minutes
}

public String generateRefreshToken(UserDetails userDetails) {
    return generateToken(userDetails, 1000 * 60 * 60 * 24 * 7); // 7 jours
}
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    public boolean validateToken(String token, String userEmail) {
        final String username = extractUsername(token);
        return (username.equals(userEmail) && !isTokenExpired(token));
    }
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    
        return claims.get("roles", List.class);
    }
    
}
