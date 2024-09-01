package com.example.backend.member.mapper;

import com.example.backend.member.dto.MemberDTO;

public interface MemberMapper {
    public int save(MemberDTO memberDTO);
    public boolean existUser(MemberDTO memberDTO);
    public String getPasswordByUserId(MemberDTO memberDTO);
}

