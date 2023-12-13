package com.example.apigateway.client;

import com.example.apigateway.data.dto.request.CheckRoleRequestDto;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@FeignClient(name = "auth-service", url = "http://localhost:8080/auth")
@Validated
public interface AuthClient {
    @GetMapping("/check/token")
    Boolean checkToken(@NonNull String token);

    @GetMapping("/check/role")
    boolean checkRole(@RequestBody CheckRoleRequestDto requestDto);

    @GetMapping("/get/claims")
    Claims getAllClaimsFromToken(@NonNull String token);
}
