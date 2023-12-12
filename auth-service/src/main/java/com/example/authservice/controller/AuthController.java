package com.example.authservice.controller;

import com.example.authservice.data.dto.request.AuthRequest;
import com.example.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
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
}
