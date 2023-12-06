package com.example.demo.dto;

import com.example.demo.enumeration.ContactType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationResponseDto {

    @Schema(description = "The reservation id")
    private long id;
    @Schema(description = "The reservation day")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDay;
    @Schema(description = "The reservation description")
    private String description;
    @Schema(description = "The contact method")
    private ContactType contactType;
    @Schema(description = "The contact detail")
    private String contactDetail;
    @Schema(description = "The user identifier")
    private String userId;
}
