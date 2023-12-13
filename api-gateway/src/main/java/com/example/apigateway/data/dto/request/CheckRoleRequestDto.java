package com.example.apigateway.data.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckRoleRequestDto {
    @NotNull
    private String token;
    @NotNull
    private String basePath;
}
