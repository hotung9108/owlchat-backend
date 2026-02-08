package com.owl.user_service.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class GatewayDocsOpenApiConfig {
    @Bean
    public GroupedOpenApi gatewayDocs() {
        return GroupedOpenApi.builder()
                .group("gateway")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.setServers(
                            List.of(new Server().url("http://localhost:8080/user-service")));
                })

                .build();
    }

    @Bean
    public GroupedOpenApi serviceDocs() {
        return GroupedOpenApi.builder()
                .group("service")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi -> openApi.setServers(
                        List.of(new Server().url("http://localhost:8081"))))
                .build();
    }
}
