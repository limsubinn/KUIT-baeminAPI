package com.example.baemin.common.exception.handler;

import com.example.baemin.common.exception.AddressException;
import com.example.baemin.common.exception.UserException;
import com.example.baemin.common.response.BaseErrorResponse;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.baemin.common.response.status.BaseExceptionResponseStatus.INVALID_ADDRESS_VALUE;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class AddressExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddressException.class)
    public BaseErrorResponse handle_AddressException(AddressException e) {
        log.error("[handle_AddressException]", e);
        return new BaseErrorResponse(INVALID_ADDRESS_VALUE, e.getMessage());
    }

}