package com.example.backend.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private String user_id;
    private String password;
    private String created_at;
    private String updated_at;
}
