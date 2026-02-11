package com.owl.chat_service.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class GatewayTrustHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
                
        String accountId = request.getHeaders().getFirst("X-Account-Id");
        // if (accountId == null) {
        // return false;
        // }
        System.err.println("AccountId = " + accountId);
        if (accountId != null) {
            attributes.put("userId", accountId);
        } else {
            attributes.put("userId", "anonymous-" + System.currentTimeMillis());
        }
        String userId = (String) attributes.get("userId");
        System.err.println("UserId = " + userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
    }

}
