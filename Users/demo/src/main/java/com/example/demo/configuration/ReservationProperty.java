package com.example.demo.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "reservations")
public class ReservationProperty {

    private String create;
    private String update;
    private String delete;
    private String search;
    private String deleteAll;
}
