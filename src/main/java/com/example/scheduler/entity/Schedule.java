package com.example.scheduler.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {

    private Long id;
    private User user;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private boolean isImportant;
    private String createdBy;
    private String schedulePassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
