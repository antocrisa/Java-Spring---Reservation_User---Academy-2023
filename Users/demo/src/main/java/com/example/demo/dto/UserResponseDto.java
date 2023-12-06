package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {

    private long id;
    private String name;
    private String surname;
    private List<ReservationResponseDto> reservations;
}
