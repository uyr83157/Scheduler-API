package com.example.scheduler.controller;

import com.example.scheduler.dto.UserGetRequestDto;
import com.example.scheduler.dto.UserPatchRequestDto;
import com.example.scheduler.dto.UserRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    // UserService 의존성 주입
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 등록
    @PostMapping
    public ResponseEntity<UserResponseDto> postUser(@RequestBody UserRequestDto userRequestDto) {

        // 람다식 처럼 new ResponseEntity<>(body, HttpStatus.상태)를 축약한게 ResponseEntity.상태(body)
//        return ResponseEntity.created(userService.addUser(userRequestDto);
        return new ResponseEntity<>(userService.addUser(userRequestDto), HttpStatus.CREATED);
    }

    // 특정 유저 조회
    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(@RequestBody UserGetRequestDto userGetRequestDto) {
//        return ResponseEntity.ok(userService.findUserByAccountPassword(userGetRequestDto.getUserAccount(), userGetRequestDto.getUserPassword());
        return new ResponseEntity<>(userService.findUserByAccountPassword(userGetRequestDto.getUserAccount(), userGetRequestDto.getUserPassword()), HttpStatus.OK);
    }

    // 유저 수정
    @PatchMapping("/{userAccount}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String userAccount,
                                                      @RequestBody UserPatchRequestDto userPatchRequestDto) {
//        return ResponseEntity.ok(userService.updateUser(userAccount, userPatchRequestDto));
        return new ResponseEntity<>(userService.updateUser(userAccount, userPatchRequestDto), HttpStatus.OK);
    }

    // 유저 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody UserGetRequestDto userGetRequestDto) {
        userService.deleteUser(userGetRequestDto.getUserAccount(), userGetRequestDto.getUserPassword());
//        return ResponseEntity.ok("사용자 정보 삭제에 성공했습니다.");
        return new ResponseEntity<>("사용자 정보 삭제에 성공했습니다.", HttpStatus.OK);
    }
}

