package com.owl.api_gateway.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.owl.api_gateway.utils.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return chain.filter(exchange);
        }
        // if (path.startsWith("/auth")
        // || path.startsWith("/swagger-ui")
        // || path.startsWith("/v3/api-docs")) {
        // return chain.filter(exchange);
        // }
        if (path.contains("/v3/api-docs")
                || (path.equals("/user-service/account") && "POST".equalsIgnoreCase(method))
                || (path.equals("/user-service/user") && "POST".equalsIgnoreCase(method))
                || path.contains("/swagger-ui")
                || path.contains("/swagger-resources")
                || path.contains("/webjars")
                || path.contains("/auth")) {
            return chain.filter(exchange);
        }
        // String authHeader =
        // exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String authHeader = extractAuthorization(exchange);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            authHeader = exchange.getRequest().getQueryParams().getFirst("token");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        if (!JwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String accountId = JwtUtil.extractAccountID(token);
        String username = JwtUtil.extractUsername(token);
        String role = JwtUtil.extractRole(token);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-Account-Id", accountId)
                .header("X-Username", username)
                .header("X-Role", role)
                .build();

        return chain.filter(
                exchange.mutate().request(mutatedRequest).build());
    }

    private String extractAuthorization(ServerWebExchange exchange) {
        return exchange.getRequest()
                .getHeaders()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase(HttpHeaders.AUTHORIZATION))
                .map(e -> e.getValue().get(0))
                .findFirst()
                .orElse(null);
    }

}
