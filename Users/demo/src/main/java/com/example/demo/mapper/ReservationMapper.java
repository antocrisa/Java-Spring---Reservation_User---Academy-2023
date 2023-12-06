package com.example.demo.mapper;

import com.example.demo.dto.ReservationClientResponseDto;
import com.example.demo.model.ReservationModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper {

    ReservationModel modelFromClientResponse(ReservationClientResponseDto reservationClientResponseDto);
}
