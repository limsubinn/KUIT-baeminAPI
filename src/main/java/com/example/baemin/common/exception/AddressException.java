package com.example.baemin.common.exception;

import com.example.baemin.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class AddressException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public AddressException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public AddressException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

}