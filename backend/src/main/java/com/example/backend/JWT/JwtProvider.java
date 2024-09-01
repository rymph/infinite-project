package com.example.backend.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.member.dto.MemberDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;



@Getter
@Setter
public class JwtProvider {
    private String key;
    private long expiredIn; // 10시간 유효
    public String createToken(MemberDTO dto) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiredIn);

        return JWT.create()
                .withSubject("infinite")
                .withClaim("user_id", dto.getUser_id())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(key));
    }

    public boolean validateToken(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public MemberDTO getUserDetail(String token){
        MemberDTO dto = new MemberDTO();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token);
        dto.setUser_id(decodedJWT.getClaim("user_id").asString());
        return dto;
    }
}
