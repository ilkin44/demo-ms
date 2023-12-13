package com.example.apigateway.data.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CheckRoleRequestDto {
    @NotNull
    private String token;
    @NotNull
    private String basePath;
}
