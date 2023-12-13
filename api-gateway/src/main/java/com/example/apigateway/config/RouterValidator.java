package com.example.apigateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/payment-service/v3/api-docs",
            "/order-service/v3/api-docs",
            "/swagger-ui/index.html",
            "/webjars/swagger-ui/index.html",
            "/payment/info",
            "/auth-service/v3/api-docs"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}