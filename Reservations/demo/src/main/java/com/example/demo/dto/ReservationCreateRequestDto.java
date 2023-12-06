package com.example.demo.dto;

import com.example.demo.enumeration.ContactType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationCreateRequestDto {

    @Schema(description = "The reservation day")
    @NotNull(message = "Should not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDay;
    @Schema(description = "The description")
    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    private String description;
    @Schema(description = "The contact method")
    @NotNull(message = "Should not be null")
    private ContactType contactType;
    @Schema(description = "The contact detail")
    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    private String contactDetail;
    @Schema(description = "The user identifier")
    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    private String userId;
}
