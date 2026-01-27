// package com.owl.api_gateway.config;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.core.Ordered;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;

// import com.owl.api_gateway.utils.JwtUtil;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import reactor.core.publisher.Mono;

// @Component
// public class JwtAuthFilter implements GlobalFilter, Ordered {

//     @Autowired
//     public JwtAuthFilter() {
//     }

//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         String path = exchange.getRequest().getPath().toString();

//         if (path.startsWith("/auth/login") || 
//             path.startsWith("/auth/register") || 
//             path.startsWith("/v3/api-docs") || 
//             path.startsWith("/swagger-ui") || 
//             path.contains("/swagger-ui/") || 
//             path.contains("/v3/api-docs")) {
//             return chain.filter(exchange);
//         }
//         String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//             return exchange.getResponse().setComplete();
//         }

//         String token = authHeader.substring(7);
//         try {
//             if (!JwtUtil.validateToken(token)) {
//                 exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                 return exchange.getResponse().setComplete();
//             }

//             String role = JwtUtil.extractRole(token);

//             if (role == null) {
//                 exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                 return exchange.getResponse().setComplete();
//             }

//             exchange.getRequest().mutate()
//                     .header("X-User-Id", JwtUtil.extractAccountID(token))
//                     .header("X-User-Roles", String.join(",", JwtUtil.extractRole(token)))
//                     .build();
//             return chain.filter(exchange);

//         } catch (Exception e) {
//             exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//             return exchange.getResponse().setComplete();
//         }
//     }

//     @Override
//     public int getOrder() {
//         return -1; // Set the order of the filter
//     }
// }
