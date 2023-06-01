package com.example.baemin.controller;

import com.example.baemin.common.exception.AddressException;
import com.example.baemin.common.response.BaseResponse;
import com.example.baemin.dto.address.GetAddressResponse;
import com.example.baemin.dto.address.PatchStatusRequest;
import com.example.baemin.dto.address.PostAddressRequest;
import com.example.baemin.dto.address.PostAddressResponse;
import com.example.baemin.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.INVALID_ADDRESS_VALUE;
import static com.example.baemin.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("")
    public BaseResponse<PostAddressResponse> create(@Validated @RequestBody PostAddressRequest postAddressRequest, BindingResult bindingResult) {
        log.info("[AddressController.create]");

        if (bindingResult.hasErrors()) {
            throw new AddressException(INVALID_ADDRESS_VALUE, getErrorMessages(bindingResult));
        }

        return new BaseResponse<>(addressService.create(postAddressRequest));
    }

    @GetMapping("/{userId}")
    public BaseResponse<List<GetAddressResponse>> getAddress(@PathVariable long userId) {
        log.info("[AddressController.get]");
        return new BaseResponse<>(addressService.getAddress(userId));
    }

    @PatchMapping("/{addressId}/status")
    public BaseResponse<String> updateStatus(@PathVariable long addressId,
                                             @Validated @RequestBody PatchStatusRequest patchStatusRequest, BindingResult bindingResult) {
        log.info("[AddressController.patchStatus]");

        if (bindingResult.hasErrors()) {
            throw new AddressException(INVALID_ADDRESS_VALUE, getErrorMessages(bindingResult));
        }

        addressService.updateStatus(addressId, patchStatusRequest.getStatus());
        return new BaseResponse<>(null);
    }
}
