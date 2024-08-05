package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Configuration
public class OpenApiConfig {

    private static final Logger LOGGER = Logger.getLogger(OpenApiConfig.class.getName());

    @Autowired
    private ResourceLoader resourceLoader;
    @Bean
    public OpenAPI customOpenAPI() throws IOException {
        String spec;
        Resource resource = new ClassPathResource("openapi.yml");
        try (InputStream inputStream = resource.getInputStream()) {
            spec = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.warning("Exception in the application" + e.getMessage());
            spec = null;
        }
        OpenAPI openAPI;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        openAPI = mapper.readValue(spec, OpenAPI.class);

        return openAPI;
    }

    @Bean
    public GroupedOpenApi hiddenApi() {
        return GroupedOpenApi.builder()
                .group("hidden")
                .packagesToScan("com.example.controller")
                .pathsToExclude("/api/**")
                .build();
    }
}