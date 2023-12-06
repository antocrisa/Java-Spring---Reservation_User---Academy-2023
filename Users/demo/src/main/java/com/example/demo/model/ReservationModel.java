package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationModel {

    private Long id;
    private LocalDate reservationDay;
    private String description;
    private String contactType;
    private String contactDetail;
    private String userId;
}
