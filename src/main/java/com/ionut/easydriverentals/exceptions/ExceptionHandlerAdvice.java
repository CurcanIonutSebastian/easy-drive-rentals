package com.ionut.easydriverentals.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    public ExceptionHandlerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> clientNotFoundException(ClientNotFoundException clientNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", clientNotFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<String> carNotFoundException(CarNotFoundException carNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", carNotFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<String> rentalNotFoundException(RentalNotFoundException rentalNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", rentalNotFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(EmailOrPhoneExistsException.class)
    public ResponseEntity<String> dataExistException(EmailOrPhoneExistsException dataExistException) {
        return new ResponseEntity<>(objectToString(Map.of("message", dataExistException.getMessage())), CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            String message = violation.getMessage();
            errors.put(fieldName, message);
        }
        return new ResponseEntity<>(objectToString(errors), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String defaultMessage = Objects.requireNonNull(error.getDefaultMessage());
            errors.put(error.getField(), defaultMessage);
        });
        return new ResponseEntity<>(objectToString(errors), HttpStatus.BAD_REQUEST);
    }


    private String objectToString(Object response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            log.error("Error processing response to string");
            return "Internal error";
        }
    }
}