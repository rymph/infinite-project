package com.example.backend.member.controller;

import com.example.backend.JWT.JwtProvider;
import com.example.backend.member.dto.MemberDTO;
import com.example.backend.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Autowired
    public MemberController(MemberService memberService, JwtProvider jwtProvider) {
        this.memberService = memberService;
        this.jwtProvider = jwtProvider;
    }


    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> save(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> response = new HashMap<>();
        if(memberService.existUser(memberDTO)){
            response.put("message", "userid in Use");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        int result = memberService.save(memberDTO);
        response.put("message", "SUCCESS");
        response.put("data", result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody MemberDTO dto){
        Map<String, Object> response = new HashMap<>();
        String password = memberService.getPasswordByUserId(dto);
        if(password == null){
            response.put("message", "userid doesn't exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if(!password.equals(dto.getPassword())){
            response.put("message", "password Doesn't Match");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        String token = jwtProvider.createToken(dto);

        response.put("message", "sign in Success");
        response.put("data", token);

        return ResponseEntity.ok().body(response);
    }
}
