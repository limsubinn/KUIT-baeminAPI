package com.example.baemin.service;

import com.example.baemin.common.exception.StoreException;
import com.example.baemin.dao.MenuDao;
import com.example.baemin.dao.StoreDao;
import com.example.baemin.dto.store.GetCategoryResponse;
import com.example.baemin.dto.store.GetMenuResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.STORE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;
    private final MenuDao menuDao;

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
        if (storeDao.hasStore(storeId)) {
            throw new StoreException(STORE_NOT_FOUND);
        }
    }

}
