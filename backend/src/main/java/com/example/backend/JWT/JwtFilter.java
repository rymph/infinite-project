package com.example.backend.JWT;

import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Authorization 헤더가 없거나 형식이 잘못된 경우
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header");
            return;
        }
        String token = authHeader.substring(7);
        if(jwtProvider.validateToken(token)) filterChain.doFilter(servletRequest, servletResponse);
        else httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
