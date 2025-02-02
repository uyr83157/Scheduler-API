package com.example.scheduler.exception;

// 계정 중복 에외
public class DuplicateUserAccountException extends RuntimeException {
    public DuplicateUserAccountException(String message) {
        super(message);
    }
}
