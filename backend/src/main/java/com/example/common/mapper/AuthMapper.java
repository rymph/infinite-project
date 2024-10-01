package com.example.common.mapper;

import com.example.common.dto.AuthDTO;
import com.example.common.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    public int register(AuthDTO reqDTO);
    public UserDTO findByEmailAndProvider(AuthDTO reqDTO);
}
