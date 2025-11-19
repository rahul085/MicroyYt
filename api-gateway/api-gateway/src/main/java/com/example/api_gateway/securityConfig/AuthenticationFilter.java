package com.example.api_gateway.securityConfig;

import com.example.api_gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders; // <-- ADD THIS IMPORT
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    // Using final and constructor injection is excellent practice
    private final JwtUtil jwtUtil;
    private static final String BEARER_PREFIX = "Bearer ";

    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1. Check if the header exists using the safe constant
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 2. Extract the full header value (e.g., "Bearer eyJ...")
        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        // 3. Remove the "Bearer " prefix before validation
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            authHeader = authHeader.substring(BEARER_PREFIX.length());
        } else {
            // If the header exists but is misformatted (e.g., just "random-string"), reject it
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 4. Validate the Token
        try {
            jwtUtil.validateToken(authHeader); // Validation happens on the clean token string
        } catch (Exception e) {
            System.out.println("Invalid access: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 5. Forward the request to the next step
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Highest priority
    }
}