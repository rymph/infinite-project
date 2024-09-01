package com.example.backend.member.service;

import com.example.backend.member.dto.MemberDTO;
import com.example.backend.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public int save(MemberDTO memberDTO) {
        return this.memberRepository.save(memberDTO);
    }

    public boolean existUser(MemberDTO memberDTO) {return this.memberRepository.existUser(memberDTO);}

    public String getPasswordByUserId(MemberDTO memberDTO) {return this.memberRepository.getPasswordByUserId(memberDTO);}

}
