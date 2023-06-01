package com.example.baemin.common.exception;

import com.example.baemin.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class StoreException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public StoreException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public StoreException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

}