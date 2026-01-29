package com.owl.social_service.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.owl.social_service.infrastructure.handler.WebSocketHandshakeHandler;
import com.owl.social_service.infrastructure.interceptor.WebSocketHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final WebSocketHandshakeHandler webSocketHandshakeHandler;

    public WebSocketConfig(WebSocketHandshakeInterceptor webSocketHandshakeInterceptor, WebSocketHandshakeHandler webSocketHandshakeHandler) {
        this.webSocketHandshakeInterceptor = webSocketHandshakeInterceptor;
        this.webSocketHandshakeHandler = webSocketHandshakeHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/social")
            .addInterceptors(webSocketHandshakeInterceptor)
            .setHandshakeHandler(webSocketHandshakeHandler)
            .setAllowedOriginPatterns("*"); 
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
    }
}
