package com.example.baemin.service;

import com.example.baemin.dao.StoreDao;
import com.example.baemin.dto.store.GetCategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;

    public List<GetCategoryResponse> getCategories() {
        log.info("[StoreService.getCategories]");
        return storeDao.getCategories();
    }

}
