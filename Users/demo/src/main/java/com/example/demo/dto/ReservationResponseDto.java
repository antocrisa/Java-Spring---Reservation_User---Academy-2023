package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationResponseDto {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDay;
    private String description;
    private String contactType;
    private String contactDetail;
}
