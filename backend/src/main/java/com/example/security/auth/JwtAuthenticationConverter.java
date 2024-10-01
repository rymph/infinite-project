package com.example.security.auth;

import com.example.common.util.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class JwtAuthenticationConverter implements AuthenticationConverter {

    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthenticationConverter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UsernamePasswordAuthenticationToken convert(HttpServletRequest request) throws AuthenticationException {
        log.info("JwtAuthenticationConverter executed");

        // check whether Authorization header exists
        String header = request.getHeader("Authorization");
        // if not,
        if (header == null) {
            log.info("Authorization header is null");
            // return 값이 null이면 추후 AuthenticationFilter에서 그냥 filter chain을 이어감
            // authorization이 실패하고 ExceptionTranslationFilter에 의해 EntryPoint.commence가 실행될 것
            // 이건 내가 HttpSecurity에 설정한 EntryPoint
            // 만약 null이 아닌 exception을 발생하면 AuthenticationFilter의 failureHandler를 따라갈 것
            return null;
        }
        // if so,
        else {
            header = header.trim();
            // check if header starts with 'Bearer'
            if (!header.startsWith("Bearer ")) {
                log.info("invalid authorization header executed");
                throw new BadCredentialsException("Invalid Authorization header");
            } else {
                String token = header.substring(7);
                if (!jwtUtils.validateAccessToken(token)) {
                    log.info("invalid access token executed");
                    throw new BadCredentialsException("Invalid JWT token");
                } else {
                    log.info("successfully authenticated");
                    String userId = jwtUtils.getEmail(token);
                    return UsernamePasswordAuthenticationToken.unauthenticated(userId, null);
                }
            }
        }
    }
}
