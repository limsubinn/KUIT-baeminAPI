package com.example.baemin.controller;

import com.example.baemin.common.response.BaseResponse;
import com.example.baemin.dto.store.GetCategoryResponse;
import com.example.baemin.dto.store.GetMenuResponse;
import com.example.baemin.dto.store.GetStoreResponse;
import com.example.baemin.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{lastId}")
    public BaseResponse<List<GetStoreResponse>> getStores
            (@RequestParam(required = false, value = "delivery-tips") String deliveryTip,
             @RequestParam(required = false, value = "least-order-price") String leastOrderPrice,
             @PathVariable("lastId") long lastId) {
        log.info("[StoreController.getStores]");

        if ((deliveryTip == null) && (leastOrderPrice != null)) { // 최소주문금액만
            return new BaseResponse<>(storeService.getStoresByPrice(leastOrderPrice, lastId));
        }

        if ((deliveryTip != null) && (leastOrderPrice == null)) { // 배달팁만
            return new BaseResponse<>(storeService.getStoresByTip(deliveryTip, lastId));
        }

        if ((deliveryTip != null) && (leastOrderPrice != null)) { // 최소주문금액 & 배달팁
            return new BaseResponse<>(storeService.getStoresByTipAndPrice(deliveryTip, leastOrderPrice, lastId));
        }

        return new BaseResponse<>(storeService.getStores(lastId));
    }

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
