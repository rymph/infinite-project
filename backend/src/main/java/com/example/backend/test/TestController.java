package com.example.backend.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test(){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        return ResponseEntity.ok().body(response);
    }
}
