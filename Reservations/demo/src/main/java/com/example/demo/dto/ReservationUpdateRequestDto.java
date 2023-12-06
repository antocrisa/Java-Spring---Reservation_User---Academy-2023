package com.example.demo.dto;

import com.example.demo.enumeration.ContactType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationUpdateRequestDto {

    @Schema(description = "The reservation description")
    private String description;
    @Schema(description = "The contact method")
    private ContactType contactType;
    @Schema(description = "The contact detail")
    private String contactDetail;
}
