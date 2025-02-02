package com.example.scheduler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchRequestDto {
    private String name; // 바꿀 이름 (선택 입력)
    private String email; // 바꿀 이메일 (선택 입력)
    private String userPassword; // 현재 비밀번호 (필수 입력)
    private String newPassword; // 바꿀 비밀번호 (선택 입력)
}
