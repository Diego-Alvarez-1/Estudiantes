package com.diegoAlvarez.sistema_reserva_libros.exception;

public class LibroNoDisponibleException extends RuntimeException {
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}

