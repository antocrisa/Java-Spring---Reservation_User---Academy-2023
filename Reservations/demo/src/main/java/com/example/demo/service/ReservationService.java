package com.example.demo.service;

import com.example.demo.entity.ReservationEntity;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.DataNotValidException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.model.ReservationModel;
import com.example.demo.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationModel findById(long id) {
        Optional<ReservationEntity> reservationEntity;
        try {
            reservationEntity = reservationRepository.findById(id);
        } catch (Exception e) {
            throw generateGenericInternalError(e);
        }
        return reservationEntity
                .map(reservationMapper::modelFromEntity)
                .orElseThrow(() -> generateEntityNotFound(id));
    }

    public List<ReservationModel> findAll(String userId) {
        try {
            return Optional.ofNullable(userId)
                    .map(reservationRepository::findByUserId)
                    .orElse(reservationRepository.findAll())
                    .stream()
                    .map(reservationMapper::modelFromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw generateGenericInternalError(e);
        }
    }

    public ReservationModel save(ReservationModel reservationModel) {
        boolean alreadyPresent;
        try {
            alreadyPresent = reservationRepository.findByReservationDay(reservationModel.getReservationDay()).isPresent();
        } catch (Exception e) {
            throw generateGenericInternalError(e);
        }
        if (alreadyPresent) {
            throw new DataNotValidException("Day already booked", ErrorCode.DAY_ALREADY_BOOKED);
        }
        ReservationEntity reservationEntity = reservationMapper.entityFromModel(reservationModel);
        try {
            return reservationMapper.modelFromEntity(reservationRepository.save(reservationEntity));
        } catch (Exception e) {
            throw generateGenericInternalError(e);
        }
    }

    public ReservationModel update(ReservationModel reservationModel, long id) {
        Optional<ReservationEntity> reservationEntity;
        try {
            reservationEntity = reservationRepository.findById(id)
                    .map(entity -> {
                        Optional.ofNullable(reservationModel.getContactType())
                                .map(Object::toString)
                                .ifPresent(entity::setContactType);
                        Optional.ofNullable(reservationModel.getContactDetail())
                                .ifPresent(entity::setContactDetail);
                        Optional.ofNullable(reservationModel.getDescription())
                                .ifPresent(entity::setDescription);
                        return reservationRepository.save(entity);
                    });
        } catch (Exception e) {
            throw generateGenericInternalError(e);
        }
        return reservationEntity
                .map(reservationMapper::modelFromEntity)
                .orElseThrow(() -> generateEntityNotFound(id));
    }

    public void delete(long id) {
        boolean hasBeenDeleted;
        try {
            hasBeenDeleted = reservationRepository.findById(id)
                    .map(reservationEntity -> {
                        reservationRepository.delete(reservationEntity);
                        return true;
                    })
                    .orElse(false);
        } catch (Exception e) {
            throw generateGenericInternalError(e);
        }
        if (!hasBeenDeleted) {
            throw generateEntityNotFound(id);
        }
    }

    private InternalErrorException generateGenericInternalError(Exception e) {
        return new InternalErrorException("Something went wrong", e, ErrorCode.INTERNAL_ERROR);
    }

    private EntityNotFoundException generateEntityNotFound(long id) {
        return new EntityNotFoundException("No data found for id " + id, ErrorCode.DATA_NOT_FOUND);
    }
}
