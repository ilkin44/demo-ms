package com.example.authservice.data.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
}