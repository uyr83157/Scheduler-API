package com.example.scheduler.dto;
// DTO: 데이터 전송을 위한 객체 (요청 데이터)


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleRequestDto {
    private String title;           // 일정 제목
    private String description;     // 일정 내용
    private LocalDateTime dateTime; // 일정 해당 날짜+시간
    private String userAccount; // 유저 식별용 계정명
    private String schedulePassword; // 일정 비밀번호
}
