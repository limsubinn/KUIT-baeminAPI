package com.example.baemin.controller;

import com.example.baemin.common.response.BaseResponse;
import com.example.baemin.dto.store.GetCategoryResponse;
import com.example.baemin.dto.store.GetMenuResponse;
import com.example.baemin.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/categories")
    public BaseResponse<List<GetCategoryResponse>> getCategories() {
        log.info("[StoreController.getCategories]");
        return new BaseResponse<>(storeService.getCategories());
    }

    @GetMapping("/{storeId}/menus")
    public BaseResponse<List<GetMenuResponse>> getMenus(@PathVariable long storeId) {
        log.info("[StoreController.getMenus]");
        return new BaseResponse<>(storeService.getMenus(storeId));
    }

}
