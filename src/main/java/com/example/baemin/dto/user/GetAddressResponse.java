package com.example.baemin.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressResponse {
    private Long addressId;
    private String defaultAddress;
    private String detailAddress;
    private String type;
    private double x;
    private double y;
    private String status;
}