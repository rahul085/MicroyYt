package com.example.api_gateway.securityConfig;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        1. Log the request (just to see it working )
        System.out.println("Gateway Filter: Request received for "+exchange.getRequest().getPath());

//        2. check if the user has authorization header
        if(!exchange.getRequest().getHeaders().containsKey("Authorization")){
            System.out.println("ERROR: No authorization header found!");

//            3.Reject the request
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

//            4. If header exists , grab it
        String tokenHeader = exchange.getRequest().getHeaders().get("Authorization").get(0);
        System.out.println("Token found : "+tokenHeader);

        // 5. Logic to validate token goes here (We will simulate it for now)
        // For now, we just allow it if the header is present.

//        6. Forward the request to the next step
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
