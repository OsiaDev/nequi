package com.nequi.prueba.common.validation;

import com.nequi.prueba.exception.CustomException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ObjectValidator {

    private final Validator validator;

    @SneakyThrows
    public <T> T validate (T object) {
        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if(errors.isEmpty())
            return object;
        else {
            String message = errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            throw new CustomException(HttpStatus.BAD_REQUEST, message);
        }
    }

}
