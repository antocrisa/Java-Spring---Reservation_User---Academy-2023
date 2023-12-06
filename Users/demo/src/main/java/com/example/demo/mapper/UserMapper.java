package com.example.demo.mapper;

import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.dto.UserCreateRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserUpdateRequestDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.model.ReservationModel;
import com.example.demo.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserEntity entityFromModel(UserModel userModel);
    UserModel modelFromEntity(UserEntity userEntity);
    UserResponseDto responseFromModel(UserModel userModel);
    UserModel modelFromCreateRequest(UserCreateRequestDto userCreateRequestDto);
    UserModel modelFromUpdateRequest(UserUpdateRequestDto userUpdateRequestDto);

    //ReservationResponseDto reservationResponseFromModel(ReservationModel reservationModel);
}
