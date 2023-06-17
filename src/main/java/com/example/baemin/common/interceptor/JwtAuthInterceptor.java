package com.example.baemin.common.interceptor;

import com.example.baemin.common.exception.jwt.JwtExpiredTokenException;
import com.example.baemin.common.exception.jwt.JwtInvalidTokenException;
import com.example.baemin.common.exception.jwt.JwtNoTokenException;
import com.example.baemin.common.exception.jwt.JwtUnsupportedTokenException;
import com.example.baemin.service.AuthService;
import com.example.baemin.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    // 인가 과정 -> 컨트롤러 호출 전에 진행되어야 한다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("[JwtAuthInterceptor.preHandle]");

        String accessToken = resolveAccessToken(request);
        validateAccessToken(accessToken);

        String email = jwtTokenProvider.getPrincipal(accessToken);
        validatePayload(email);

        long userId = authService.getUserIdByEmail(email);
        request.setAttribute("userId", userId);
        return true;
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateToken(token);
        return token.substring(JWT_TOKEN_PREFIX.length());
    }

    private void validateToken(String token) {
        if (token == null) {
            throw new JwtNoTokenException(TOKEN_NOT_FOUND);
        }
        if (!token.startsWith(JWT_TOKEN_PREFIX)) {
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        }
    }

    private void validateAccessToken(String accessToken) {
        if (jwtTokenProvider.isExpiredToken(accessToken)) {
            throw new JwtExpiredTokenException(EXPIRED_TOKEN);
        }
    }

    private void validatePayload(String email) {
        if (email == null) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        }
    }

}