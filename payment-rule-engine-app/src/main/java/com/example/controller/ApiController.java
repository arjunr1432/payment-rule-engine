package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger LOGGER = Logger.getLogger(ApiController.class.getName());

    @Autowired
    private ResourceLoader resourceLoader;
    @GetMapping("/spec")
    public ResponseEntity<String> getApiSpec() throws IOException {
        String spec;
        Resource resource = new ClassPathResource("openapi.yml");
        try (InputStream inputStream = resource.getInputStream()) {
            spec = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.warning("Exception in the application" + e.getMessage());
            spec = null;
        }
        return ResponseEntity.ok(spec);
    }
}
