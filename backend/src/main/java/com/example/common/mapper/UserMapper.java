package com.example.common.mapper;

import com.example.common.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public UserDTO findByEmail(UserDTO reqDTO);
}

