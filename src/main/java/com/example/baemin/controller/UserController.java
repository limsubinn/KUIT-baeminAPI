package com.example.baemin.controller;

import com.example.baemin.common.exception.UserException;
import com.example.baemin.common.response.BaseResponse;
import com.example.baemin.dto.user.*;
import com.example.baemin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/login")
    public BaseResponse<PostLoginResponse> login(@Validated @RequestBody PostLoginRequest postLoginRequest, BindingResult bindingResult) {
        log.info("[UserController.login]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.login(postLoginRequest));
    }

    @PatchMapping("/{userId}/nickname")
    public BaseResponse<String> updateNickname(@PathVariable long userId,
                                               @Validated @RequestBody PatchNicknameRequest patchNicknameRequest, BindingResult bindingResult) {
        log.info("[UserController.updateNickname]");

        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }

        userService.updateNickname(userId, patchNicknameRequest.getNickname());
        return new BaseResponse<>(null);
    }

    @PatchMapping("/{userId}/deleted")
    public BaseResponse<String> deleteUser(@PathVariable long userId) {
        log.info("[UserController.deleteUser]");

        userService.deleteUser(userId);
        return new BaseResponse<>(null);
    }

    @GetMapping("/{userId}/address")
    public BaseResponse<List<GetAddressResponse>> getAddress(@PathVariable long userId) {
        log.info("[AddressController.get]");
        return new BaseResponse<>(userService.getAddress(userId));
    }

}
