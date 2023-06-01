package com.example.baemin.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMenuResponse {
    private Long menuId;
    private String category;
    private String name;
    private int price;
}
