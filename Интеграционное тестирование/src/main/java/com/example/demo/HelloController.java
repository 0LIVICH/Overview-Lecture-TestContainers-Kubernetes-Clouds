package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${netology.profile.dev:false}")
    private boolean isDevProfile;

    @GetMapping("/")
    public String hello() {
        if (isDevProfile) {
            return "Hello from DEV environment!";
        } else {
            return "Hello from PROD environment!";
        }
    }

    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }
} 