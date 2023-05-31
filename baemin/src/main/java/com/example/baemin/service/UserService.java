package com.example.baemin.service;

import com.example.baemin.common.exception.UserException;
import com.example.baemin.dao.UserDao;
import com.example.baemin.dto.user.PostUserRequest;
import com.example.baemin.dto.user.PostUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.DUPLICATE_EMAIL;
import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.DUPLICATE_NICKNAME;

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

}
