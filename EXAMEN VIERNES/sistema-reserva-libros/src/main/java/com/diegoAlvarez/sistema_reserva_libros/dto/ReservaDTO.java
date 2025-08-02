package com.diegoAlvarez.sistema_reserva_libros.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ReservaDTO {
    
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
    
    @NotNull(message = "El libro es obligatorio")
    private Long libroId;
    
    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReserva;
    
    @AssertTrue(message = "La fecha de reserva debe ser posterior o igual a la fecha actual")
    private boolean isFechaReservaValida() {
        return fechaReserva == null || !fechaReserva.isBefore(LocalDate.now());
    }
}