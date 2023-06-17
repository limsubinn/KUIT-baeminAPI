package com.example.baemin.service;

import com.example.baemin.common.exception.UserException;
import com.example.baemin.common.exception.jwt.JwtUnauthorizedTokenException;
import com.example.baemin.dao.UserDao;
import com.example.baemin.dto.auth.PostLoginRequest;
import com.example.baemin.dto.auth.PostLoginResponse;
import com.example.baemin.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public PostLoginResponse login(PostLoginRequest postLoginRequest) {
        log.info("[AuthService.login]");

        String email = postLoginRequest.getEmail();

        // 이메일 유효성 확인
        long userId;
        try {
            userId = userDao.getUserIdByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserException(EMAIL_NOT_FOUND);
        }

        // userId, password 일치 여부 확인
        matchUser(userId, postLoginRequest.getPassword());

        // jwt 갱신
        String updatedJwt = jwtTokenProvider.createToken(email);

        return new PostLoginResponse(userId, updatedJwt);
    }

    private void matchUser(long userId, String password) {
        String encodedPassword = userDao.getPasswordByUserId(userId);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UserException(PASSWORD_NO_MATCH);
        }
    }

    public long getUserIdByEmail(String email) {
        try {
            return userDao.getUserIdByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new JwtUnauthorizedTokenException(TOKEN_MISMATCH);
        }
    }

}
