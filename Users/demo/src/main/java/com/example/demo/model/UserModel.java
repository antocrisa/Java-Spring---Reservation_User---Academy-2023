package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserModel {

    private Long id;
    private String name;
    private String surname;
    private UUID internalId;
    private List<ReservationModel> reservations;
}
