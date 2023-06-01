package com.example.baemin.service;

import com.example.baemin.common.exception.AddressException;
import com.example.baemin.common.exception.DatabaseException;
import com.example.baemin.common.exception.UserException;
import com.example.baemin.dao.AddressDao;
import com.example.baemin.dao.UserDao;
import com.example.baemin.dto.address.PostAddressRequest;
import com.example.baemin.dto.address.PostAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.*;

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

    public void updateStatus(long addressId, String status) {
        log.info("[AddressService.updateStatus]");

        // addressId 검사
        validateAddress(addressId);

        // status 값 검사
        validateStatus(status);

        // 상태 업데이트
        int affectedRows = addressDao.updateStatus(addressId, status);
        if (affectedRows != 1) { // db error
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    private void validateUser(Long userId) {
        if (userDao.hasUser(userId)) {
            throw new UserException(USER_NOT_FOUND);
        }
    }

    private void validateType(String type) {
        if (!(type.equals("home")) && !(type.equals("company")) && !(type.equals("etc"))) {
            throw new AddressException(INVALID_ADDRESS_TYPE);
        }
    }

    private void validateAddress(Long addressId) {
        if (addressDao.hasAddress(addressId)) {
            throw new AddressException(ADDRESS_NOT_FOUND);
        }
    }

    private void validateStatus(String status) {
        if (!(status.equals("Y")) && !(status.equals("N"))) {
            throw new AddressException(INVALID_ADDRESS_STATUS);
        }
    }
}
