package com.example.demo.exception;

import com.example.demo.enumeration.ErrorCode;

public class InternalErrorException extends CustomErrorException {

    public InternalErrorException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }
}
