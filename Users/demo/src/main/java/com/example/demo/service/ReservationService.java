package com.example.demo.service;

import com.example.demo.configuration.ReservationProperty;
import com.example.demo.dto.ReservationClientDeleteRequestDto;
import com.example.demo.dto.ReservationClientResponseDto;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.model.ReservationModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final RestTemplate restTemplate;
    private final ValidationService validationService;
    private final ReservationMapper reservationMapper;
    private final ReservationProperty reservationProperty;

    public List<ReservationModel> search(UUID internalId) {
        List<ReservationClientResponseDto> reservationClientResponseDtos;
        String uri = UriComponentsBuilder.fromUriString(reservationProperty.getSearch())
                .queryParam("userId", internalId.toString())
                .toUriString();
        try {
            reservationClientResponseDtos = restTemplate.exchange(uri,
                            HttpMethod.GET,
                            new HttpEntity<>(null),
                            new ParameterizedTypeReference<List<ReservationClientResponseDto>>() {})
                    .getBody();
        } catch (Exception e) {
            throw generateInternalError(e);
        }
        return reservationClientResponseDtos.stream()
                .map(reservationClientResponseDto -> {
                    validationService.doValidate(reservationClientResponseDto);
                    return reservationClientResponseDto;
                })
                .map(reservationMapper::modelFromClientResponse)
                .collect(Collectors.toList());
    }

    public void removeAll(UUID internalId) {
        HttpEntity<ReservationClientDeleteRequestDto> httpEntity =
                new HttpEntity<>(ReservationClientDeleteRequestDto.builder()
                        .userId(internalId.toString()).build());
        try {
            restTemplate.exchange(
                    reservationProperty.getDeleteAll(),
                    HttpMethod.POST,
                    httpEntity,
                    Void.class);
        } catch (Exception e) {
            throw generateInternalError(e);
        }
    }

    private InternalErrorException generateInternalError(Exception e) {
        return new InternalErrorException("Something went wrong", e, ErrorCode.INTERNAL_ERROR);
    }
}
