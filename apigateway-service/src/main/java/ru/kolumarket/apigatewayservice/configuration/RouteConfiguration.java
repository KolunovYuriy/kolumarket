package ru.kolumarket.apigatewayservice.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/v1/auth/**")
        return builder.routes()
                .route(r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.rewritePath("/api/v1/auth/(?.*)", "/${remains}")
                                )
                        .uri("lb://AUTH-SERVICE/")
                        .id("auth-service"))

                .route(r -> r.path("/api/v1/order/**")
                        .filters(f -> f.rewritePath("/api/v1/order/(?.*)", "/${remains}")
                                )
                        .uri("lb://ORDER-SERVICE/")
                        .id("order-service"))
                .route(r -> r.path("/api/v1/product/**")
                        .filters(f -> f.rewritePath("/api/v1/product/(?.*)", "/${remains}")
                        )
                        .uri("lb://PRODUCT-SERVICE/")
                        .id("product-service"))
                .route(r -> r.path("/api/v1/warehouse/**")
                        .filters(f -> f.rewritePath("/api/v1/warehouse/(?.*)", "/${remains}")
                        )
                        .uri("lb://AUTH-SERVICE/")
                        .id("warehouse-service"))
                .build();

    }
}
