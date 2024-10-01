package com.example.common.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long userId;
    private String email;
    private String password;
    private String provider;
    private String roles;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
