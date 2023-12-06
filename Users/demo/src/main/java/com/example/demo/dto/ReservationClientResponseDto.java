package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationClientResponseDto {

    @NotNull(message = "Should not be null")
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDay;
    private String description;
    @Pattern(regexp = "PHONE|EMAIL", message = "Should contains PHONE or EMAIL")
    private String contactType;
    private String contactDetail;
    private String userId;
}
