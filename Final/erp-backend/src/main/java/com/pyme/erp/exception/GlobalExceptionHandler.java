package com.pyme.erp.exception;

import com.pyme.erp.dto.response.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleBusinessException(BusinessException ex) {
        log.error("Error de negocio: {}", ex.getMessage());
        
        ResponseWrapper<Object> response = ResponseWrapper.error(
            ex.getMessage(),
            HttpStatus.CONFLICT.value(),
            ex.getErrorCode().getCode()
        );
        
        return response.toResponseEntity();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        
        List<ResponseWrapper.FieldError> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> new ResponseWrapper.FieldError(
                fieldError.getField(),
                fieldError.getCode(),
                fieldError.getDefaultMessage()
            ))
            .collect(Collectors.toList());
        
        ResponseWrapper<Object> response = ResponseWrapper.validationError(errors);
        return response.toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Object>> handleGeneralException(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        
        ResponseWrapper<Object> response = ResponseWrapper.error(
            "Error interno del servidor",
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        
        return response.toResponseEntity();
    }
}