package com.docautomation.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthAPI {
    @GetMapping("/api/health")
    public String health() {
        return "Backend is healthy! Anas Alhajri Ul hasan Alshammari";
    }
}