package com.example.apigateway.config.filter;

import com.example.apigateway.client.AuthClient;
import com.example.apigateway.config.RouterValidator;
import com.example.apigateway.data.dto.request.CheckRoleRequestDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

//
@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {


    private final RouterValidator routerValidator;

    private final AuthClient authClient;

    //    @Value("${jwt.prefix}")
    public String TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String requestPath = request.getURI().getPath();


        if (this.isAuthMissing(request) || this.isPrefixMissing(request))
            return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

        final String token = this.getAuthHeader(request);

        System.out.println("test");

        if (authClient.checkToken(token))
            return this.onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
//
        if (authClient.checkRole(CheckRoleRequestDto
                .builder()
                .token(token)
                .basePath(requestPath)
                .build()))
            System.out.println(request.getURI().getPath());

//            return this.onError(exchange, "Authorization header is invalid", HttpStatus.FORBIDDEN);

        this.populateRequestWithHeaders(exchange, token);

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        var header = request.getHeaders().getOrEmpty("Authorization").get(0);
        return header.replace(TOKEN_PREFIX, "").trim();
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private boolean isPrefixMissing(ServerHttpRequest request) {
        var header = request.getHeaders().getFirst("Authorization");
        assert header != null;
        return !header.startsWith(TOKEN_PREFIX);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = authClient.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("roles", String.valueOf(claims.get("roles")))
                .build();
    }
}