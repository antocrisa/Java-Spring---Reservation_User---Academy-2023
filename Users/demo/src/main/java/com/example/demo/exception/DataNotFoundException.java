package com.example.demo.exception;

import com.example.demo.enumeration.ErrorCode;

public class DataNotFoundException extends CustomErrorCodeException {

    public DataNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
