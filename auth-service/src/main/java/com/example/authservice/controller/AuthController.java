package com.example.authservice.controller;

import com.example.authservice.data.dto.request.AuthRequest;
import com.example.authservice.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        var token = authService.authenticate(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Token", token.getAccessToken());
        headers.set("Refresh-Token", token.getRefreshToken());

        return ResponseEntity.ok().headers(headers).body("authenticated");
    }

    @GetMapping("/check/token")
    Boolean isTokenValid(@NonNull @RequestParam String token) {
        return authService.isTokenValid(token);
    }

    @GetMapping("/check/role")
    boolean getPaymentsByOrderId(@NonNull String token, @NonNull String requestPath) {
        return authService.checkRole(token,requestPath);
    }

    @GetMapping("/get/claims")
    Claims getAllClaimsFromToken(@NonNull String token) {
        return authService.getClaims(token);
    }
}
