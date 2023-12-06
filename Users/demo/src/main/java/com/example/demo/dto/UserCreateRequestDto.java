package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "The create request model for a user")
public class UserCreateRequestDto {

    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    @Schema(description = "The name of the user")
    private String name;
    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    @Schema(description = "The surname of the user")
    private String surname;
}
