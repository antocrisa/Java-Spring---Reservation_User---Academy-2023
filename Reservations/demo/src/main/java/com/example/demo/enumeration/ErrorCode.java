package com.example.demo.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "Error codes: \n" +
        "* `INTERNAL_ERROR` - Something went wrong\n" +
        "* `DATA_NOT_FOUND` - No data found\n" +
        "* `DATA_NOT_VALID` - Invalid data provided\n" +
        "* `DAY_ALREADY_BOOKED` - Day already booked\n" +
        "")
public enum ErrorCode {

    INTERNAL_ERROR, DATA_NOT_FOUND, DATA_NOT_VALID, DAY_ALREADY_BOOKED
}
