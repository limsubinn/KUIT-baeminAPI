package com.example.baemin.controller;

import com.example.baemin.common.exception.UserException;
import com.example.baemin.common.response.BaseResponse;
import com.example.baemin.dto.user.LoginUserRequest;
import com.example.baemin.dto.user.LoginUserResponse;
import com.example.baemin.dto.user.PostUserRequest;
import com.example.baemin.dto.user.PostUserResponse;
import com.example.baemin.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public BaseResponse<PostUserResponse> signUp(@Validated @RequestBody PostUserRequest postUserRequest, BindingResult bindingResult) {
        log.info("[UserController.signUp]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.signUp(postUserRequest));
    }

    @GetMapping("/login")
    public BaseResponse<LoginUserResponse> login(@Validated @RequestBody LoginUserRequest loginUserRequest, BindingResult bindingResult) {
        log.info("[UserController.login]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.login(loginUserRequest));
    }

}
