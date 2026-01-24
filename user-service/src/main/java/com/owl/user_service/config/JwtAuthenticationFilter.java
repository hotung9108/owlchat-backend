package com.owl.user_service.config;

import com.owl.user_service.infrastructure.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // @Override
    // protected void doFilterInternal(HttpServletRequest request,
    // HttpServletResponse response, FilterChain filterChain)
    // throws ServletException, IOException {
    // String authHeader = request.getHeader("Authorization");

    // if (authHeader != null && authHeader.startsWith("Bearer ")) {
    // String token = authHeader.substring(7);
    // if (JwtUtil.validateToken(token)) {
    // String username = JwtUtil.extractUsername(token);
    // String role = JwtUtil.extractRole(token);

    // UsernamePasswordAuthenticationToken authentication = new
    // UsernamePasswordAuthenticationToken(
    // username, null, Collections.singletonList(new SimpleGrantedAuthority(role))
    // );

    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // }
    // }

    // filterChain.doFilter(request, response);
    // }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // ===== BYPASS PUBLIC APIs =====
        if (path.startsWith("/auth") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                (path.equals("/account") && method.equals("POST"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing Authorization Header");
            return;
        }

        String token = authHeader.substring(7);

        if (!JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }

        String username = JwtUtil.extractUsername(token);
        String role = JwtUtil.extractRole(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role)));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}