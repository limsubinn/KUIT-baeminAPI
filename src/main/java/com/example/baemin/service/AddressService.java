package com.example.baemin.service;

import com.example.baemin.common.exception.UserException;
import com.example.baemin.dao.AddressDao;
import com.example.baemin.dao.UserDao;
import com.example.baemin.dto.user.PostAddressRequest;
import com.example.baemin.dto.user.PostAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        // DB insert & addressId 반환
        long addressId = addressDao.createAddress(postAddressRequest);

        return new PostAddressResponse(addressId);
    }

    private void validateUser(Long userId) {
        if (userDao.hasUser(userId)) {
            throw new UserException(USER_NOT_FOUND);
        }
    }
}
