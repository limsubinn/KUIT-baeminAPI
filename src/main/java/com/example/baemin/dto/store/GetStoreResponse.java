package com.example.baemin.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreResponse {
    private Long storeId;
    private String name;
    private String category;
    private int deliveryTip;
    private int leastOrderPrice;
}