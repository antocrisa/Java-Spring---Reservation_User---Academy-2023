package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ReservationService reservationService;

    public UserModel createUser(UserModel userModel) {
        userModel.setInternalId(UUID.randomUUID());
        UserEntity userEntity;
        try {
            userEntity = userRepository.save(userMapper.entityFromModel(userModel));
        } catch (Exception e) {
            throw generateInternalError(e);
        }
        return userMapper.modelFromEntity(userEntity);
    }

    public List<UserModel> findAll() {
        try {
            return userRepository.findAll().stream()
                    .map(userMapper::modelFromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw generateInternalError(e);
        }
    }

    public UserModel findById(long userId) {
        Optional<UserEntity> userEntity;
        try {
            userEntity = userRepository.findById(userId);
        } catch (Exception e) {
            throw generateInternalError(e);
        }
        return userEntity
                .map(userMapper::modelFromEntity)
                .map(userModel -> {
                    userModel.setReservations(reservationService.search(userModel.getInternalId()));
                    return userModel;
                })
                .orElseThrow(() -> generateDataNotFound(userId));
    }

    public UserModel updateUser(long userId, UserModel userModel) {
        Optional<UserEntity> userEntity;
        try {
            userEntity = userRepository.findById(userId)
                    .map(entity -> {
                        Optional.ofNullable(userModel.getName())
                                .filter(StringUtils::isNotBlank)
                                .ifPresent(entity::setName);
                        Optional.ofNullable(userModel.getSurname())
                                .filter(StringUtils::isNotBlank)
                                .ifPresent(entity::setSurname);
                        return userRepository.save(entity);
                    });
        } catch (Exception e) {
            throw generateInternalError(e);
        }
        return userEntity
                .map(entity -> findById(entity.getId()))
                .orElseThrow(() -> generateDataNotFound(userId));
    }

    public void deleteUser(long userId) {
        Optional<UserEntity> userEntity;
        try {
            userEntity = userRepository.findById(userId);
        } catch (Exception e) {
            throw generateInternalError(e);
        }
        userEntity
                .map(UserEntity::getInternalId)
                .ifPresentOrElse(reservationService::removeAll, () -> {
                    throw generateDataNotFound(userId);
                });
    }

    private InternalErrorException generateInternalError(Exception e) {
        return new InternalErrorException("Something went wrong", e, ErrorCode.INTERNAL_ERROR);
    }

    private DataNotFoundException generateDataNotFound(long userId) {
        return new DataNotFoundException("No data found for id: " + userId, ErrorCode.DATA_NOT_FOUND);
    }
}
