package com.example.baemin.service;

import com.example.baemin.common.exception.StoreException;
import com.example.baemin.dao.MenuDao;
import com.example.baemin.dao.StoreDao;
import com.example.baemin.dto.store.GetCategoryResponse;
import com.example.baemin.dto.store.GetMenuResponse;
import com.example.baemin.dto.store.GetStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.INVALID_STORE_SORTED_VALUE;
import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.STORE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;
    private final MenuDao menuDao;

    public List<GetStoreResponse> getStoresByTipAndPrice(String deliveryTip, String leastOrderPrice, long lastId) {
        log.info("[StoreService.getStoresByTipAndPrice]");

        // 정수 변환
        int tip = toInteger(deliveryTip);
        int price = toInteger(leastOrderPrice);

        return storeDao.getStoresByTipAndPrice(tip, price, lastId);
    }

    public List<GetStoreResponse> getStoresByTip(String deliveryTip, long lastId) {
        log.info("[StoreService.getStoresByTip]");

        // 정수 변환
        int tip = toInteger(deliveryTip);

        return storeDao.getStoresByTip(tip, lastId);
    }

    public List<GetStoreResponse> getStoresByPrice(String leastOrderPrice, long lastId) {
        log.info("[StoreService.getStoresByPrice]");

        // 정수 변환
        int price = toInteger(leastOrderPrice);

        return storeDao.getStoresByPrice(price, lastId);
    }
    public List<GetStoreResponse> getStores(long lastId) {
        log.info("[StoreService.getStores]");
        return storeDao.getStores(lastId);
    }

    public List<GetCategoryResponse> getCategories() {
        log.info("[StoreService.getCategories]");
        return storeDao.getCategories();
    }

    public List<GetMenuResponse> getMenus(Long storeId) {
        log.info("[StoreService.getMenus]");

        // storeId 검사
        validateStore(storeId);

        return menuDao.getMenus(storeId);
    }

    private void validateStore(Long storeId) {
        if (!storeDao.hasStore(storeId)) {
            throw new StoreException(STORE_NOT_FOUND);
        }
    }

    private int toInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new StoreException(INVALID_STORE_SORTED_VALUE);
        }
    }

}
