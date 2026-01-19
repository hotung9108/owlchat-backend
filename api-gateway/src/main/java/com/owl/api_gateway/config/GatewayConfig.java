package com.owl.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

import com.owl.api_gateway.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public WebFilter jwtAuthenticationWebFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        return jwtAuthenticationFilter;
    }
}
