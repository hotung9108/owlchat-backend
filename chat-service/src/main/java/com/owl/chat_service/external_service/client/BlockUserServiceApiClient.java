package com.owl.chat_service.external_service.client;

import java.time.Duration;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.owl.chat_service.external_service.dto.BlockDto;
import com.owl.chat_service.infrastructure.properties.ExternalServicesURL;

import reactor.core.publisher.Mono;

@Component
public class BlockUserServiceApiClient {
    private final WebClient webClient;

    public BlockUserServiceApiClient(WebClient.Builder builder, ExternalServicesURL properties) {
        webClient = builder.baseUrl(properties.getSocial()).build();
    }

    public BlockDto getUserBlockUser(String blockerId, String blockedId) {
        return webClient.get().uri("/blocker/{blockerId}/blocked/{blockedId}", blockerId, blockedId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError,
                response -> Mono.error(new RuntimeException("Block not found")))
            .onStatus(HttpStatusCode::is5xxServerError,
                response -> Mono.error(new RuntimeException("External server error")))
            .bodyToMono(BlockDto.class)
            .timeout(Duration.ofSeconds(3))
            .block();
    }
}
