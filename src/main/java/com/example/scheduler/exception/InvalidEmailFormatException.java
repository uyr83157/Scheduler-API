package com.example.scheduler.exception;

// 이메일 형식 예외
public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String message) {
        super(message);
    }
}
