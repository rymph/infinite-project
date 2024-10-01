package com.example.backend.auth.service;


import com.example.common.dto.AuthDTO;
import com.example.common.dto.UserDTO;
import com.example.common.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public int register(AuthDTO reqDTO) {
        log.info("AuthService /signup");
        log.info("original reqDTO : {}", reqDTO.toString());

        if(reqDTO.getProvider().equals("infinite")){
            reqDTO.setPassword(passwordEncoder.encode(reqDTO.getPassword()));
        }

        log.info("modified reqDTO : {}", reqDTO.toString());
        return this.authMapper.register(reqDTO);
    }

    public UserDTO findByEmailAndProvider(AuthDTO reqDTO) {
        return this.authMapper.findByEmailAndProvider(reqDTO);
    }
}
