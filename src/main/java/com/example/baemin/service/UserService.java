package com.example.baemin.service;

import com.example.baemin.common.exception.DatabaseException;
import com.example.baemin.common.exception.UserException;
import com.example.baemin.dao.AddressDao;
import com.example.baemin.dao.UserDao;
import com.example.baemin.dto.user.*;
import com.example.baemin.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final AddressDao addressDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

        // JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(postUserRequest.getEmail());

        return new PostUserResponse(userId, jwt);
    }

    public PostLoginResponse login(PostLoginRequest postLoginRequest) {
        log.info("[UserService.createUser]");

        // get userId
        Long userId = getUserIdByEmail(postLoginRequest.getEmail());

        // userId, password 일치 여부 확인
        matchUser(userId, postLoginRequest.getPassword());

        return new PostLoginResponse(userId);
    }

    public void updateNickname(long userId, String nickname) {
        log.info("[UserService.updateNickname]");

        // userId 검사
        validateUser(userId);

        // 닉네임 중복 검사
        validateNickname(nickname);

        // 닉네임 업데이트
        int affectedRows = userDao.updateNickname(userId, nickname);
        if (affectedRows != 1) { // db error
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void deleteUser(long userId) {
        log.info("[UserService.deleteUser]");

        // userId 검사
        validateUser(userId);

        // status = 'deleted' 변경
        int affectedRows = userDao.deleteUser(userId);
        if (affectedRows != 1) { // db error
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public List<GetAddressResponse> getAddress(Long userId) {
        log.info("[UserService.getAddress]");

        // userId 검사
        validateUser(userId);

        return addressDao.getAddress(userId);
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

    private void validateUser(Long userId) {
        if (userDao.hasUser(userId)) {
            throw new UserException(USER_NOT_FOUND);
        }
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
