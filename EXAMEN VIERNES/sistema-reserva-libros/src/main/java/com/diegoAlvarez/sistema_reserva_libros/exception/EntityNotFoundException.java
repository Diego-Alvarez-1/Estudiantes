
package com.diegoAlvarez.sistema_reserva_libros.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String mensaje) {
        super(mensaje);
    }
}