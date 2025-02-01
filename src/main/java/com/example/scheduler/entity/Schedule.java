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
public class Schedule {

    private Long scheduleId; // 일정 식별자
    private User userAccount; // 일정 작성한 유저
    private String title; // 일정 제목
    private String description; // 일정 내용
    private LocalDateTime dateTime; // 일정 해당 날짜+시간
    private boolean isImportant; // 일정의 중요 여부
    private String schedulePassword; // 일정 패스워드
    private LocalDateTime createdAt; // 최초 일정 작성날짜+시간
    private LocalDateTime updatedAt; // 마지막으로 일정 수정한 날짜+시간


}
