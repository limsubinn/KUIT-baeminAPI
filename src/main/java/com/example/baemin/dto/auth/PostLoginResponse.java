package com.example.baemin.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLoginResponse {
    private long userId;
    private String jwt;
}
