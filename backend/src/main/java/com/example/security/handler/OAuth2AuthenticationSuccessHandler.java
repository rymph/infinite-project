package com.example.security.handler;

import com.example.backend.auth.controller.AuthController;
import com.example.backend.auth.service.AuthService;
import com.example.common.dto.AuthDTO;
import com.example.common.dto.UserDTO;
import com.example.common.mapper.AuthMapper;
import com.example.common.util.CookieUtils;
import com.example.security.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.security.oauth.CustomOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Environment env;
    private final AuthMapper authMapper;
    private static final String AUTHORIZATION_REQUEST_COOKIE_NAME = "OAUTH2_AUTHORIZATION_REQUEST";
    private static final String REGISTRATION_COOKIE_NAME = "INFINITE_INFO_PROVIDED";
    private static final String LOGIN_COOKIE_NAME = "INFINITE_LOGIN_AVAILABLE";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String registrationRedirectUri = env.getProperty("registration-redirect-uri");
        String loginRedirectUri = env.getProperty("login-redirect-uri");
        CookieUtils.deleteCookie(request, response, AUTHORIZATION_REQUEST_COOKIE_NAME);

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = customOAuth2User.getAttribute("email");
        Assert.hasText(email, "email cannot be empty");
        String provider = customOAuth2User.getProvider();

        AuthDTO authDTO = new AuthDTO(email, null, null, provider);
        UserDTO userDTO = authMapper.findByEmailAndProvider(authDTO);
        String cookieValue = "email="+email+"&provider="+provider;
        /*
        String cookieValue = "email=" +
                URLEncoder.encode(email, StandardCharsets.UTF_8) +
                "&" +
                "provider=" +
                URLEncoder.encode(provider, StandardCharsets.UTF_8);
        */
        // 회원가입이 안 되어 있다면
        if (userDTO == null) {
            log.info("cookie value : {}", cookieValue);
            CookieUtils.addCookie(response, REGISTRATION_COOKIE_NAME, cookieValue, 180);
            response.sendRedirect(registrationRedirectUri);
        } else { // 되어있다면
            CookieUtils.addCookie(response, LOGIN_COOKIE_NAME, cookieValue, 180);
            response.sendRedirect(loginRedirectUri);
        }

    }

}
