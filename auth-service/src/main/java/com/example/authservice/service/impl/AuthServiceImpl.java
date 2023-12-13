package com.example.authservice.service.impl;

import com.example.authservice.config.MyUserPrincipal;
import com.example.authservice.data.dto.request.AuthRequest;
import com.example.authservice.data.dto.response.AuthResponse;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Object principal = authenticate.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        MyUserPrincipal myUserPrincipal = MyUserPrincipal
                .builder()
                .username(userDetails.getUsername())
                .authorities(userDetails.getAuthorities())
                .password(userDetails.getPassword())
                .build();

        return jwtService.generateToken(myUserPrincipal);
    }

    @Override
    public boolean isTokenValid(String token) {
//        return jwtService.isTokenValid(token);
        return true;
    }

    @Override
    public Claims getClaims(String token) {
        return jwtService.getAllClaimsFromToken(token);
    }

    @Override
    public boolean checkRole(String token, String requestPath) {
        return true;
    }

}