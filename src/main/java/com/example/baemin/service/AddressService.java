package com.example.baemin.service;

import com.example.baemin.dao.AddressDao;
import com.example.baemin.dto.user.PostAddressRequest;
import com.example.baemin.dto.user.PostAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressDao addressDao;

    public PostAddressResponse create(PostAddressRequest postAddressRequest) {
        log.info("[AddressService.createAddress]");

        // DB insert & addressId 반환
        long addressId = addressDao.createAddress(postAddressRequest);

        return new PostAddressResponse(addressId);
    }
}
