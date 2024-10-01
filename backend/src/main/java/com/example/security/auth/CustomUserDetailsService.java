package com.example.security.auth;

import com.example.backend.auth.service.AuthService;
import com.example.common.dto.AuthDTO;
import com.example.common.dto.UserDTO;
import com.example.common.mapper.AuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthMapper authMapper;

    @Autowired
    public CustomUserDetailsService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user {}", username);
        AuthDTO authDTO = new AuthDTO(username, null, null, "infinite");
        UserDTO userDTO = authMapper.findByEmailAndProvider(authDTO);
        if(userDTO == null) {
            log.info("User {} not found", username);
            throw new UsernameNotFoundException(username);
        }

        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String provider = userDTO.getProvider();
        String roles = userDTO.getRoles();

        UserDetails userDetails =  CustomUserDetails.builder()
                .email(email)
                .password(password)
                .provider(provider)
                .roles(roles)
                .build();

        log.info("userDetails: {}", userDetails);
        return userDetails;
    }
}
