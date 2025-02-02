package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String userAccount;
    private String userPassword;
    private LocalDateTime userCreatedAt;
    private LocalDateTime userUpdatedAt;

}
