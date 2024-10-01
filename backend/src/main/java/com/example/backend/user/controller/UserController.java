package com.example.backend.user.controller;

import com.example.common.dto.UserDTO;
import com.example.backend.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> Test() {
        log.info("hi");
        log.info("strategy : {}", SecurityContextHolder.getContextHolderStrategy().toString());
        log.info("strategy getcontext : {}", SecurityContextHolder.getContextHolderStrategy().getContext().toString());
        log.info("principal : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ResponseEntity.ok().header("Content-Type", "application/json").body("{\"message\":\"success\"}");
    }

    @GetMapping("/fuck")
    public ResponseEntity<?> Test2() {
        return ResponseEntity.ok().header("Content-Type", "application/json").body("{\"message\":\"success\"}");
    }

    public ResponseEntity<?> userInfo(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().header("Content-Type", "application/json").body("{\"message\":\"success\"}");
    }
}
