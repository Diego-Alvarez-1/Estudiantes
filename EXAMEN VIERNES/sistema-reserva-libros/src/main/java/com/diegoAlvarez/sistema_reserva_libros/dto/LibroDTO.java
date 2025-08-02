
package com.diegoAlvarez.sistema_reserva_libros.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibroDTO {
    
    @NotBlank(message = "El título del libro no puede estar vacío")
    @Size(min = 2, max = 200, message = "El título debe tener entre 2 y 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "El código del libro no puede estar vacío")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres")
    private String codigo;
    
    @NotBlank(message = "El autor del libro no puede estar vacío")
    @Size(min = 2, max = 100, message = "El autor debe tener entre 2 y 100 caracteres")
    private String autor;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}