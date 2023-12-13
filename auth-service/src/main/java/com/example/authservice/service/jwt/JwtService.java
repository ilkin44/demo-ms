package com.example.authservice.service.jwt;


import com.example.authservice.config.MyUserPrincipal;
import com.example.authservice.data.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateAccessToken(Claims extraClaims, String username) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public AuthResponse generateToken(MyUserPrincipal myUserPrincipal) {
        String accessToken = generateAccessToken(extraClaims(myUserPrincipal), myUserPrincipal.getUsername());
        String refreshToken = generateRefreshToken(myUserPrincipal.getUsername());
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    private Claims extraClaims(MyUserPrincipal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();
        Set<String> userRoles = new HashSet<>();
        for (GrantedAuthority role : userPrincipal.getAuthorities()) {
            // TODO: 12/12/2023 add for db user ("ROLE_" +)
            userRoles.add(role.getAuthority());
        }
        claims.put("roles", userRoles);
        return Jwts.claims(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}