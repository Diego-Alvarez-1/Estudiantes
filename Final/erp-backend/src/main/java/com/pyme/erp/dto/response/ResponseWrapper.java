package com.pyme.erp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {
    
    private T data;
    private String message;
    private int status;
    private String errorCode;
    private List<FieldError> errors;

    public static <T> ResponseWrapper<T> success(T data, String message) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setData(data);
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    public static <T> ResponseWrapper<T> success(T data) {
        return success(data, "Operación exitosa");
    }

    public static <T> ResponseWrapper<T> error(String message, int status) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

    public static <T> ResponseWrapper<T> error(String message, int status, String errorCode) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setMessage(message);
        response.setStatus(status);
        response.setErrorCode(errorCode);
        return response;
    }

    public static <T> ResponseWrapper<T> validationError(List<FieldError> errors) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setMessage("Error de validación");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(errors);
        return response;
    }

    public ResponseEntity<ResponseWrapper<T>> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }

    @Getter
    @Setter
    public static class FieldError {
        private String field;
        private String code;
        private String message;

        public FieldError(String field, String code, String message) {
            this.field = field;
            this.code = code;
            this.message = message;
        }
    }
}