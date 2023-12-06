package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "The update request model for a user")
public class UserUpdateRequestDto {

    @Schema(description = "The name of the user")
    private String name;
    @Schema(description = "The surname of the user")
    private String surname;
}
