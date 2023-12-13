package com.example.authservice.service;

import com.example.authservice.data.dto.request.AuthRequest;
import com.example.authservice.data.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);

    boolean isTokenValid(String token);

    Claims getClaims(String token);

    boolean checkRole(String token, String requestPath);
}
