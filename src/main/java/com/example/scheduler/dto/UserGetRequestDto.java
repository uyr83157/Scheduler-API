package com.example.scheduler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetRequestDto {
    private String userAccount;
    private String userPassword;
}
