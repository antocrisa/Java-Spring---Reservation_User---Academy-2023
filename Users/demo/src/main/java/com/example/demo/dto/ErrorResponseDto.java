package com.example.demo.dto;

import com.example.demo.enumeration.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {

    private ErrorCode errorCode;
    private String errorDescription;
}
