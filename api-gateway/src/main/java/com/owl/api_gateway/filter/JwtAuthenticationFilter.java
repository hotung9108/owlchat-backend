package com.owl.api_gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.http.server.ServerHttpRequest;
// import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.owl.api_gateway.utils.JwtUtil;

import reactor.core.publisher.Mono;

// @Component
public class JwtAuthenticationFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        // if (path.startsWith("/swagger-ui") ||
        // path.startsWith("/v3/api-docs") ||
        // path.contains("/user-service/v3/api-docs") ||
        // path.contains("/chat-service/v3/api-docs") ||
        // path.equals("/swagger-ui.html") ||
        // path.equals("/swagger-ui/index.html")) {
        // return chain.filter(exchange); // Cho qua mà không check token
        // }
        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.contains("/user-service/v3/api-docs") ||
                path.contains("/chat-service/v3/api-docs") ||
                path.equals("/swagger-ui.html") ||
                path.equals("/swagger-ui/index.html") ||
                path.startsWith("/webjars/")) { 
            return chain.filter(exchange); 
        }
        ServerHttpRequest request = exchange.getRequest();
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!JwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String username = JwtUtil.extractUsername(token);
        String role = JwtUtil.extractRole(token);
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-Username", username)
                .header("X-Role", role)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }
}
