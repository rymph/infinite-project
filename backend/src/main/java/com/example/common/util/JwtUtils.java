package com.example.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.dto.JwtResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.accessTokenSecretKey}")
    private String accessTokenSecretKey;
    @Value("${jwt.refreshTokenSecretKey}")
    private String refreshTokenSecretKey;
    @Value("${jwt.accessTokenExpiresInSeconds}")
    private long accessTokenExpiresInSeconds;
    @Value("${jwt.refreshTokenExpiresInSeconds}")
    private long refreshTokenExpiresInSeconds;

    @Autowired
    private final ObjectMapper objectMapper;

    public String createAccessToken(String email, String roles, String provider, long cur){

        return JWT.create()
                .withSubject("jwt-access")
                .withClaim("email", email)
                .withClaim("roles", roles)
                .withClaim("provider", provider)
                .withIssuedAt(new Date(cur))
                .withExpiresAt(new Date(cur + accessTokenExpiresInSeconds * 1000))
                .sign(Algorithm.HMAC256(this.accessTokenSecretKey));
    }

    public String createRefreshToken(long cur){

        return JWT.create()
                .withSubject("jwt-refresh")
                .withIssuedAt(new Date(cur))
                .withExpiresAt(new Date(cur + refreshTokenExpiresInSeconds * 1000))
                .sign(Algorithm.HMAC256(this.refreshTokenSecretKey));
    }

    public boolean validateAccessToken(String token){
        try {
            JWT.require(Algorithm.HMAC256(this.accessTokenSecretKey)).build().verify(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public String getEmail(String token){
        try {
            return JWT.require(Algorithm.HMAC256(this.accessTokenSecretKey)).build().verify(token).getClaim("email").asString();
        }catch(Exception e){
            return null;
        }
    }

    public String getProvider(String token){
        try{
            return JWT.require(Algorithm.HMAC256(this.accessTokenSecretKey)).build().verify(token).getClaim("provider").asString();
        }catch(Exception e){
            return null;
        }
    }

    public Date getAccessTokenExpiresAt(String token){
        try{
            Date exp = JWT.require(Algorithm.HMAC256(this.accessTokenSecretKey)).build().verify(token).getClaim("exp").asDate();
            return new Date(exp.getTime());
        }catch(Exception e){
            return null;
        }
    }

    public Date getRefreshTokenExpiresAt(String token){
        try{
            Date exp = JWT.require(Algorithm.HMAC256(this.refreshTokenSecretKey)).build().verify(token).getClaim("exp").asDate();
            return new Date(exp.getTime());
        }catch(Exception e){
            return null;
        }
    }

    public boolean validateRefreshToken(String token){
        try{
            JWT.require(Algorithm.HMAC256(this.refreshTokenSecretKey)).build().verify(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
