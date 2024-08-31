package com.example.backend.member.controller;

import com.example.backend.member.dto.MemberDTO;
import com.example.backend.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberDTO> save(@RequestBody MemberDTO memberDTO) {
        int result = memberService.save(memberDTO);
        // result 가지고 검증 logic 작성하기
        return ResponseEntity.ok().body(memberDTO);
    }
}
