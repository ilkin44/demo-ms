package com.example.apigateway.config;

import com.example.apigateway.config.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private  AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("payment-service-route", r -> r.path("/payment-service/v3/api-docs")
                        .uri("lb://PAYMENT-SERVICE"))

                .route("payment-service-route", r -> r.path("/payment/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PAYMENT-SERVICE"))

                .route("order-service-route", r -> r.path("/order/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ORDER-SERVICE"))

                .route("order-service-route", r -> r.path("/order/**", "/order-service/v3/api-docs")
                        .uri("lb://ORDER-SERVICE"))

                .route("auth-service-route", r -> r.path("/auth/**", "/auth-service/v3/api-docs")
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }
}
