package com.example.apigateway.config.filter;

import com.example.apigateway.client.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {


//    private final RouterValidator routerValidator;

    private final AuthClient authClient;

    public String TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String requestPath = request.getURI().getPath();

        if (this.isAuthMissing(request) || this.isPrefixMissing(request)) {
            return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
        }

        System.out.println("step1 ");
        final String token = this.getAuthHeader(request);

        if (!isValidToken(token)) {
            return this.onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
        }
        System.out.println("step2 ");

        if (!isAuthorized(token, requestPath)) {
            return this.onError(exchange, "Authorization header is invalid", HttpStatus.FORBIDDEN);
        }
        System.out.println("step3 ");

        this.populateRequestWithHeaders(exchange, token);
        return chain.filter(exchange);
    }

    private boolean isAuthorized(String token, String requestPath) {
        return authClient.checkRole(token, requestPath);
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
        List<String> roles = authClient.getAllRoles(token);
        exchange.getRequest().mutate()
                .header("roles", String.valueOf(roles))
                .build();
    }

    private boolean isValidToken(String token) {
        return authClient.checkToken(token);
    }
}