package com.example.scheduler.service;


import com.example.scheduler.dto.UserPatchRequestDto;
import com.example.scheduler.dto.UserRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.entity.User;
import com.example.scheduler.exception.AuthenticationException;
import com.example.scheduler.exception.DuplicateEmailException;
import com.example.scheduler.exception.DuplicateUserAccountException;
import com.example.scheduler.exception.InvalidEmailFormatException;
import com.example.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 이메일 형식 체크용 정규표현식
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // 유저 정보 등록
    public UserResponseDto addUser(UserRequestDto userRequestDto) {

        // userRequestDto 로 요청 받은 getEmail 값을 EMAIL_PATTERN 정규식과 비교(matches)
        // 정규 표현식에 맞으면 ture, 틀리면 false, 그런데 if 문이 실행되려면 ture 가 되어야 하니 ! 으로 값반전
        if (!EMAIL_PATTERN.matcher(userRequestDto.getEmail()).matches()) {
            throw new InvalidEmailFormatException("이메일 형식이 올바르지 않습니다.");
        }

        // 이메일 중복 체크
        if (userRepository.findUserByEmail(userRequestDto.getEmail()) != null) {
            throw new DuplicateEmailException("이미 사용중인 이메일 입니다.");
        }

        // 계정 중복 체크
        if (userRepository.findUserByAccount(userRequestDto.getUserAccount()) != null) {
            throw new DuplicateUserAccountException("이미 사용중인 계정입니다.");
        }

        // 클라이언트에게 요청 받은 DTO 데이터를 user 객체 엔티티 데이터로 변환
        // userId는 PK로 자동 할당, userCreatedAt,userUpdatedAt는 NOW() 로 자동 할당
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setUserAccount(userRequestDto.getUserAccount());
        user.setUserPassword(userRequestDto.getUserPassword());


        // userRepository 클래스의 addUser 메서드로 user 객체 엔티티 데이터를 넘김
        // 정상적으로 넘겼으면 값이 1 = ture -> !반전, 실패했으면 0 = false -> !반전 반환
        if (!(userRepository.addUser(user) > 0)) {
            // 실패했으면 RuntimeException 으로 예외처리
            throw new RuntimeException("사용자 정보 등록에 실패했습니다. 다시 시도해 주세요.");
        }

        // 저장된 유저 정보 중 아까 입력받은 UserAccount 값으로 조회
        User addUser = userRepository.findUserByAccount(userRequestDto.getUserAccount());

        // 응답 DTO 생성 (중복 되므로 나중에 리팩터링 최적화 필요)
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(addUser.getUserId());
        userResponseDto.setName(addUser.getName());
        userResponseDto.setEmail(addUser.getEmail());
        userResponseDto.setUserAccount(addUser.getUserAccount());
        userResponseDto.setUserPassword(addUser.getUserPassword());
        userResponseDto.setUserCreatedAt(addUser.getUserCreatedAt());
        // userUpdatedAt는 등록 시에는 노출하지 않음 (불필요)

        return userResponseDto;
    }

    // 특정 유저 정보 조회 (userAccount 로 조회하고, userPassword 로 검증)
    public UserResponseDto findUserByAccountPassword(String userAccount, String userPassword) {

        // userAccount 로 찾은 User 객체를 user 변수로 삽입
        User user = userRepository.findUserByAccount(userAccount);

        // 요청한 userAccount 에 해당하는 객체가 없거나, userAccount 에 해당하는 객체와 요청한 userPassword 값이 불일치 할 경우
        if (user == null || !user.getUserPassword().equals(userPassword)) {
            // 계정과 비밀번호 중 무엇이 틀렸는지 구분하지 않음 (보안 강화)
            throw new AuthenticationException("계정 또는 비밀번호를 확인해주세요.");
        }

        // 응답 DTO 생성 (중복 되므로 나중에 리팩터링 최적화 필요)
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setUserAccount(user.getUserAccount());
        userResponseDto.setUserPassword(user.getUserPassword());
        userResponseDto.setUserCreatedAt(user.getUserCreatedAt());
        userResponseDto.setUserUpdatedAt(user.getUserUpdatedAt());

        return userResponseDto;
    }


    // 특정 유저 정보 수정 (계정, 비밀번호, 수정할 UserRequestDto 객체를 인자로 받음)
    public UserResponseDto updateUser(String userAccount, UserPatchRequestDto userPatchRequestDto) {

        User user = userRepository.findUserByAccount(userAccount);

        if (user == null || !user.getUserPassword().equals(userPatchRequestDto.getUserPassword())) {
            // 계정과 비밀번호 중 무엇이 틀렸는지 구분하지 않음 (보안 강화)
            throw new AuthenticationException("계정 또는 비밀번호를 확인해주세요.");
        }


        // 이메일 데이터를 새로 요청 받고 있고 + DB에 등록된 이메일과 요청받은 이메일이 다르고 + 공백제거처리(trim) 했을 때 비어있지 않다면 실행
        if (userPatchRequestDto.getEmail() != null
                && !user.getEmail().equals(userPatchRequestDto.getEmail())
                && !userPatchRequestDto.getEmail().trim().isEmpty()) {

            if (!EMAIL_PATTERN.matcher(userPatchRequestDto.getEmail()).matches()) {
                throw new InvalidEmailFormatException("이메일 형식이 올바르지 않습니다.");
            }

            if (userRepository.findUserByEmail(userPatchRequestDto.getEmail()) != null) {
                throw new DuplicateEmailException("이미 사용중인 이메일 입니다.");
            }

            // 문제 없으면 이메일 값 업데이트
            user.setEmail(userPatchRequestDto.getEmail());
        }

        // 같은 방식으로 이름도 수정
        if (userPatchRequestDto.getName() != null && !userPatchRequestDto.getName().trim().isEmpty()) {
            user.setName(userPatchRequestDto.getName());
        }

        // 같은 방식으로 비밀번호도 수정
        if (userPatchRequestDto.getNewPassword() != null && !userPatchRequestDto.getNewPassword().trim().isEmpty()) {
            user.setUserPassword(userPatchRequestDto.getNewPassword());
        }

        // 계정(userAccount)은 고유값으로 간주하고 수정 불가능


        // 이 부분도 중복된 부분이 많아 리팩터링 필요해 보임
        if (!(userRepository.updateUser(user) > 0)) {
            throw new RuntimeException("사용자 정보 수정에 실패했습니다. 다시 시도해 주세요.");
        }

        // addUser 같은 경우엔 기존 userAccount 값이 없고 새로 요청 DTO 로 userAccount 를 입력받은 반면,
        // 지금은 새로 요청 받은 DTO 에 userAccount 가 없고 기존 userAccount 데이터가 있기에 userRequestDto.getUserAccount() 말고 userAccount 로 조회
        User updateUser = userRepository.findUserByAccount(userAccount);

        // 응답 DTO 생성 (중복 되므로 나중에 리팩토링 최적화 필요)
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(updateUser.getUserId());
        userResponseDto.setName(updateUser.getName());
        userResponseDto.setEmail(updateUser.getEmail());
        userResponseDto.setUserAccount(updateUser.getUserAccount());
        userResponseDto.setUserPassword(updateUser.getUserPassword());
        userResponseDto.setUserCreatedAt(updateUser.getUserCreatedAt());
        userResponseDto.setUserUpdatedAt(updateUser.getUserUpdatedAt());

        return userResponseDto;
    }

    // 특정 유저 정보 삭제
    public void deleteUser(String userAccount, String userPassword) {

        // 이 부분도 계속 겹치기에 리팩터링 필요해보임
        User user = userRepository.findUserByAccount(userAccount);
        if (user == null || !user.getUserPassword().equals(userPassword)) {
            throw new AuthenticationException("계정 또는 비밀번호를 확인해주세요.");
        }

        if (!(userRepository.deleteUserById(user.getUserId()) > 0)) {
            throw new RuntimeException("사용자 정보 삭제에 실패했습니다. 다시 시도해 주세요.");
        }

    }


}
