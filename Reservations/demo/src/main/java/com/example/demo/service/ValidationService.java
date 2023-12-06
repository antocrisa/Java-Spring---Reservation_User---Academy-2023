package com.example.demo.service;

import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.DataNotValidException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class ValidationService {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public void doValidate(Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validatorFactory.getValidator().validate(o);
        if (!constraintViolations.isEmpty()) {
            throw new DataNotValidException(ErrorCode.DATA_NOT_VALID, constraintViolations);
        }
    }
}
