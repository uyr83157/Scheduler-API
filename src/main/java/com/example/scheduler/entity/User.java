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
    private String name; // 유저 이름 (동명이인을 고려해서 중복 가능)
    private String email; // 유저 이메일
    private String userAccount; // 유저 식별용 계정명
}
