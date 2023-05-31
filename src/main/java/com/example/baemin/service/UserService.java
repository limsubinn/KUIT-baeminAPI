package com.example.baemin.service;

import com.example.baemin.common.exception.UserException;
import com.example.baemin.dao.UserDao;
import com.example.baemin.dto.user.LoginUserRequest;
import com.example.baemin.dto.user.LoginUserResponse;
import com.example.baemin.dto.user.PostUserRequest;
import com.example.baemin.dto.user.PostUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public PostUserResponse signUp(PostUserRequest postUserRequest) {
        log.info("[UserService.createUser]");

        // 이메일, 닉네임 중복 검사
        validateEmail(postUserRequest.getEmail());
        validateNickname(postUserRequest.getNickname());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());
        postUserRequest.resetPassword(encodedPassword);

        // DB insert & userId 반환
        long userId = userDao.createUser(postUserRequest);

        return new PostUserResponse(userId);
    }

    private void validateEmail(String email) {
        if (userDao.hasDuplicateEmail(email)) {
            throw new UserException(DUPLICATE_EMAIL);
        }
    }

    private void validateNickname(String nickname) {
        if (userDao.hasDuplicateNickName(nickname)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }

    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        log.info("[UserService.createUser]");

        // get userId
        Long userId = getUserIdByEmail(loginUserRequest.getEmail());

        // userId, password 일치 여부 확인
        matchUser(userId, loginUserRequest.getPassword());

        return new LoginUserResponse(userId);
    }

    public long getUserIdByEmail(String email) {
        return userDao.getUserIdByEmail(email);
    }

    private void matchUser(Long userId, String password) {
        String encodedPassword = userDao.getPasswordByUserId(userId);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UserException(PASSWORD_NO_MATCH);
        }
    }

}
