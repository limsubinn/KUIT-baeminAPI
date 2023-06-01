package com.example.baemin.service;

import com.example.baemin.common.exception.AddressException;
import com.example.baemin.common.exception.UserException;
import com.example.baemin.dao.AddressDao;
import com.example.baemin.dao.UserDao;
import com.example.baemin.dto.address.GetAddressResponse;
import com.example.baemin.dto.address.PostAddressRequest;
import com.example.baemin.dto.address.PostAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.INVALID_ADDRESS_TYPE;
import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressDao addressDao;
    private final UserDao userDao;

    public PostAddressResponse create(PostAddressRequest postAddressRequest) {
        log.info("[AddressService.createAddress]");

        // userId 검사
        validateUser(postAddressRequest.getUserId());

        // type 검사
        validateType(postAddressRequest.getType());

        // DB insert & addressId 반환
        long addressId = addressDao.createAddress(postAddressRequest);

        return new PostAddressResponse(addressId);
    }

    public List<GetAddressResponse> getAddress(Long userId) {
        log.info("[AddressService.getAddress]");

        // userId 검사
        validateUser(userId);

        return addressDao.getAddress(userId);
    }

    private void validateUser(Long userId) {
        if (userDao.hasUser(userId)) {
            throw new UserException(USER_NOT_FOUND);
        }
    }

    private void validateType(String type) {
        if (!(type.equals("home")) || !(type.equals("company")) || !(type.equals("etc"))) {
            throw new AddressException(INVALID_ADDRESS_TYPE);
        }
    }
}
