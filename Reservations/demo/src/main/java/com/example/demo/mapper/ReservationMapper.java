package com.example.demo.mapper;

import com.example.demo.dto.ReservationCreateRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.dto.ReservationUpdateRequestDto;
import com.example.demo.entity.ReservationEntity;
import com.example.demo.model.ReservationModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper {

    ReservationModel modelFromEntity(ReservationEntity reservationEntity);
    ReservationResponseDto responseFromModel(ReservationModel reservationModel);

    ReservationModel modelFromCreateRequest(ReservationCreateRequestDto reservationCreateRequestDto);
    ReservationEntity entityFromModel(ReservationModel reservationModel);

    ReservationModel modelFromUpdateRequest(ReservationUpdateRequestDto reservationUpdateRequestDto);
}
