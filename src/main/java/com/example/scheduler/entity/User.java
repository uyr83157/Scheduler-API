package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter // lombok 자동 Getter 생성
@Setter // lombok 자동 Setter 생성
@NoArgsConstructor // lombok 자동 필드 생성자 생성
@AllArgsConstructor // lombok 자동 기본 생성자 생성
public class User {

    private Long userId; // 유저 식별자
    private String name; // 유저 이름
    private String email; // 유저 이메일
    private String userPassword; // 유저 패스워드
    private LocalDateTime signUpDate; // 유저 가입 날짜+시간
    private int  scheduleCount; // 총 작성한 일정 수
    private String userStatus; // 유저 제재 상태
    private Byte userLevel; // 유저 권한 레벨

}
