package com.example.backend.admin.service;

import com.example.common.dto.UserDTO;
import com.example.common.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(UserDTO reqDTO) {
        // userDTO와 reqDTO 패스워드 match 확인
        return true;
    }
}
