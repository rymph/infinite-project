package com.example.backend.member.repository;

import com.example.backend.member.dto.MemberDTO;
import com.example.backend.member.mapper.MemberMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository implements MemberMapper {

    private final SqlSessionTemplate sm;

    @Autowired
    public MemberRepository(SqlSessionTemplate sm) {
        this.sm = sm;
    }

    @Override
    public int save(MemberDTO memberDTO) {
        return sm.insert("save", memberDTO);
    }
}
