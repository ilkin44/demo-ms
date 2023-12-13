package com.example.apigateway.config;

import com.example.apigateway.config.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("payment-service-route", r -> r.path("/payment/**", "/payment-service/v3/api-docs")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PAYMENT-SERVICE"))
                
                .route("order-service-route", r -> r.path("/order/**", "/order-service/v3/api-docs")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ORDER-SERVICE"))

                .route("auth-service-route", r -> r.path("/auth/**", "/auth-service/v3/api-docs")
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }
}
