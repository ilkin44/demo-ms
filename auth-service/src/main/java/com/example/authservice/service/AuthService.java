package com.example.authservice.service;

import com.example.authservice.data.dto.request.AuthRequest;
import com.example.authservice.data.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);

    AuthResponse refreshToken(String refreshToken);
}
