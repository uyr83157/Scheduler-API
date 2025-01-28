package com.example.scheduler.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImportantSchedule {

    private Long id;
    private User user;
    private Schedule schedule;
    private Long createdBy;
    private LocalDateTime importantRegisteredAt;
    private String importantRegisteredBy;

}
