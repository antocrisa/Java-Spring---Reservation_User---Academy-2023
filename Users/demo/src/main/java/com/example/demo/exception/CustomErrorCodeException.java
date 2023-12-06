package com.example.demo.exception;

import com.example.demo.enumeration.ErrorCode;
import lombok.Getter;

@Getter
public class CustomErrorCodeException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomErrorCodeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomErrorCodeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomErrorCodeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
