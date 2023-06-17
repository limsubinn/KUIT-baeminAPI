package com.example.baemin.common.exception.jwt;

import com.example.baemin.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class JwtUnauthorizedTokenException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtUnauthorizedTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

}