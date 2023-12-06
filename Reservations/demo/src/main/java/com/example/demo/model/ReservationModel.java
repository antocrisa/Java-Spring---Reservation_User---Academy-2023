package com.example.demo.model;

import com.example.demo.enumeration.ContactType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationModel {

    private Long id;
    private LocalDate reservationDay;
    private String description;
    private ContactType contactType;
    private String contactDetail;
    private String userId;
}
