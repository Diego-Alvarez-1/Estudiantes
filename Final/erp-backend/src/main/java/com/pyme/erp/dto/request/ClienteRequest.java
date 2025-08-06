package com.pyme.erp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @Email(message = "El formato del email no es válido")
    private String email;
    
    private String telefono;
    
    private String direccion;
    
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;
}