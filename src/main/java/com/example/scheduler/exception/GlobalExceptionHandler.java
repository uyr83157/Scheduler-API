package com.example.scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


// 전역에서 발생하는 예외를 핸들링
@ControllerAdvice
public class GlobalExceptionHandler {

    // DuplicateEmailException 예외가 발생할 때 호출됨
    // 이메일 중복
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException e) {
        // HTTP 상태 코드 409(CONFLICT): 중복된 값에 대한 충돌 + 메세지 받음
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    // 계정 중복
    @ExceptionHandler(DuplicateUserAccountException.class)
    public ResponseEntity<String> handleDuplicateUserAccountException(DuplicateUserAccountException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    // 이메일 형식 예외
    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<String> handleInvalidEmailFormatException(InvalidEmailFormatException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    // 계정 및 비밀번호 불일치
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        // HTTP 상태 코드 401(Unauthorized): 클라이언트가 인증되지 않았거나, 유효한 인증 정보가 부족하여 요청 거부
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
