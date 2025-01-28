package com.example.scheduler.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String email;
    private String userPassword;
    private LocalDateTime signUpDate;
    private int  scheduleCount;
    private String userStatus;
    private Byte userLevel;


}
