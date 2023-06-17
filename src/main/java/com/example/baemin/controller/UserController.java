package com.example.baemin.controller;

import com.example.baemin.common.argument_resolver.PreAuthorize;
import com.example.baemin.common.exception.AddressException;
import com.example.baemin.common.exception.UserException;
import com.example.baemin.common.response.BaseResponse;
import com.example.baemin.dto.user.PatchAddressStatusRequest;
import com.example.baemin.dto.user.PostAddressRequest;
import com.example.baemin.dto.user.PostAddressResponse;
import com.example.baemin.dto.user.*;
import com.example.baemin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.INVALID_ADDRESS_VALUE;
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

    @PatchMapping("/nickname")
    public BaseResponse<String> updateNickname(@PreAuthorize long userId,
                                               @Validated @RequestBody PatchNicknameRequest patchNicknameRequest, BindingResult bindingResult) {
        log.info("[UserController.updateNickname]");

        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }

        userService.updateNickname(userId, patchNicknameRequest.getNickname());
        return new BaseResponse<>(null);
    }

    @PatchMapping("/deleted")
    public BaseResponse<String> deleteUser(@PreAuthorize long userId) {
        log.info("[UserController.deleteUser]");

        userService.deleteUser(userId);
        return new BaseResponse<>(null);
    }

    @PostMapping("/address")
    public BaseResponse<PostAddressResponse> createAddress(@PreAuthorize long userId,
                                                           @Validated @RequestBody PostAddressRequest postAddressRequest, BindingResult bindingResult) {
        log.info("[UserController.createAddress]");

        if (bindingResult.hasErrors()) {
            throw new AddressException(INVALID_ADDRESS_VALUE, getErrorMessages(bindingResult));
        }

        return new BaseResponse<>(userService.createAddress(userId, postAddressRequest));
    }

    @GetMapping("/address")
    public BaseResponse<List<GetAddressResponse>> getAddress(@PreAuthorize long userId) {
        log.info("[UserController.getAddress]");
        return new BaseResponse<>(userService.getAddress(userId));
    }

    @PatchMapping("/address/{addressId}/status")
    public BaseResponse<String> updateStatus(@PreAuthorize long userId,
                                             @PathVariable long addressId,
                                             @Validated @RequestBody PatchAddressStatusRequest patchStatusRequest, BindingResult bindingResult) {
        log.info("[UserController.patchAddressStatus]");

        if (bindingResult.hasErrors()) {
            throw new AddressException(INVALID_ADDRESS_VALUE, getErrorMessages(bindingResult));
        }

        userService.updateStatus(userId, addressId, patchStatusRequest.getStatus());
        return new BaseResponse<>(null);
    }

}
