package com.example.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Setter
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private String usernameParameter = "email";
    private String passwordParameter = "password";
    private static final AntPathRequestMatcher DEFAULT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/auth/signin", "POST");

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(DEFAULT_PATH_REQUEST_MATCHER, authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String contentType = request.getContentType();

        if(!contentType.equals("application/json") && !contentType.equals("application/x-www-form-urlencoded")) {
            throw new AuthenticationServiceException("Unsupported content type: " + contentType);
        }

        String username;
        String password;
        UsernamePasswordAuthenticationToken authRequest;

        if(contentType.equals("application/json")) {
            Map<String, String> body = objectMapper.readValue(request.getInputStream(), new TypeReference<Map<String, String>>(){});
            username = body.get(this.usernameParameter);
            password = body.get(this.passwordParameter);
        }else{
            username = request.getParameter(this.usernameParameter);
            password = request.getParameter(this.passwordParameter);
        }
        log.info("username : {}", username);
        log.info("password : {}", password);

        authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
