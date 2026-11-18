package com.docautomation.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HealthAPI {

    private final JdbcTemplate jdbcTemplate;

    public HealthAPI(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Backend is healthy! Less gooooo");
        body.put("timestamp", Instant.now().toString());

        Map<String, Object> db = new LinkedHashMap<>();
        try {
            long start = System.currentTimeMillis();
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            db.put("status", result != null && result == 1 ? "UP" : "DOWN");
            db.put("responseTimeMs", System.currentTimeMillis() - start);
        } catch (Exception e) {
            db.put("status", "DOWN");
            db.put("error", e.getClass().getSimpleName());
        }
        body.put("database", db);

        boolean up = "UP".equals(db.get("status"));
        body.put("status", up ? "UP" : "DEGRADED");

        return ResponseEntity
                .status(up ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                .body(body);
    }
}