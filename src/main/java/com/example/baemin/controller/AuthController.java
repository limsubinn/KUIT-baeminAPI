package com.example.baemin.controller;

import com.example.baemin.common.argument_resolver.PreAuthorize;
import com.example.baemin.common.exception.UserException;
import com.example.baemin.common.response.BaseResponse;
import com.example.baemin.dto.auth.PostLoginRequest;
import com.example.baemin.dto.auth.PostLoginResponse;
import com.example.baemin.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static com.example.baemin.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<PostLoginResponse> login(@Validated @RequestBody PostLoginRequest postLoginRequest, BindingResult bindingResult) {
        log.info("[AuthController.login]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(authService.login(postLoginRequest));
    }

    @GetMapping("/test")
    public BaseResponse<String> checkAuthorization(@PreAuthorize long userId) {
        log.info("[AuthController.checkAuthorization]");
        return new BaseResponse<>("userId=" + userId);
    }
}