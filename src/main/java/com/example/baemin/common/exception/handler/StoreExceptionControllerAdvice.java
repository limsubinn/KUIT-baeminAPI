package com.example.baemin.common.exception.handler;

import com.example.baemin.common.exception.StoreException;
import com.example.baemin.common.response.BaseErrorResponse;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.INVALID_STORE_VALUE;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class StoreExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StoreException.class)
    public BaseErrorResponse handle_StoreException(StoreException e) {
        log.error("[handle_StoreException]", e);
        return new BaseErrorResponse(INVALID_STORE_VALUE, e.getMessage());
    }

}