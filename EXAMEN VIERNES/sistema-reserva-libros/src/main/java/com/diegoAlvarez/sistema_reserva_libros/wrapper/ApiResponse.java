package com.diegoAlvarez.sistema_reserva_libros.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {
    
    private String mensaje;
    private String estado;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T datos;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldErrorDetail> errores;

    public static <T> ApiResponse<T> success(T datos, String mensaje) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setDatos(datos);
        response.setMensaje(mensaje);
        response.setEstado("SUCCESS");
        return response;
    }

    public static <T> ApiResponse<T> error(String mensaje) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMensaje(mensaje);
        response.setEstado("ERROR");
        return response;
    }

    public static <T> ApiResponse<T> validationError(List<FieldErrorDetail> errores) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMensaje("Error de validación");
        response.setEstado("VALIDATION_ERROR");
        response.setErrores(errores);
        return response;
    }

    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<ApiResponse<T>> toResponseEntity(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FieldErrorDetail {
        private String campo;
        private String codigo;
        private String mensaje;
    }
}
