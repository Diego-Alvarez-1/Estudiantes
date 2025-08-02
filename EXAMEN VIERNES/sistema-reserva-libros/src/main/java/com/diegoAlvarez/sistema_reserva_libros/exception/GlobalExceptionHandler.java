package com.diegoAlvarez.sistema_reserva_libros.exception;

import com.diegoAlvarez.sistema_reserva_libros.wrapper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException exception) {
        
        List<ApiResponse.FieldErrorDetail> errores = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ApiResponse.FieldErrorDetail(
                        fieldError.getField(),
                        "VALIDATION_ERROR",
                        fieldError.getDefaultMessage()
                ))
                .toList();

        return ApiResponse.validationError(errores).toResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LibroNoDisponibleException.class)
    public ResponseEntity<ApiResponse<Object>> handleLibroNoDisponible(LibroNoDisponibleException ex) {
        return ApiResponse.error(ex.getMessage()).toResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StockAgotadoException.class)
    public ResponseEntity<ApiResponse<Object>> handleStockAgotado(StockAgotadoException ex) {
        return ApiResponse.error(ex.getMessage()).toResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LibroYaReservadoException.class)
    public ResponseEntity<ApiResponse<Object>> handleLibroYaReservado(LibroYaReservadoException ex) {
        return ApiResponse.error(ex.getMessage()).toResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return ApiResponse.error(ex.getMessage()).toResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        return ApiResponse.error("Error interno del servidor").toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
