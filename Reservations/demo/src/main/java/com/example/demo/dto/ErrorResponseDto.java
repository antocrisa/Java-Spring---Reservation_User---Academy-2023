package com.example.demo.dto;

import com.example.demo.enumeration.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "The error code")
    private ErrorCode errorCode;
    @Schema(description = "The error description")
    private String errorDescription;
}
