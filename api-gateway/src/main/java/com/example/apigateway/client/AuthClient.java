package com.example.apigateway.client;

import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "auth-service", url = "http://localhost:9096/auth")
@Validated
public interface AuthClient {
    @GetMapping("/check/token")
    boolean checkToken(@NonNull @RequestParam String token);

    @GetMapping("/check/role")
    boolean checkRole(@RequestParam String token, @RequestParam String requestPath);

    @GetMapping("/get/claims")
    List<String> getAllRoles(@NonNull @RequestParam String token);
}
