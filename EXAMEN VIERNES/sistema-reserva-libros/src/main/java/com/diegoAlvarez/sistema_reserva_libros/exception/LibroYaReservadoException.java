package com.diegoAlvarez.sistema_reserva_libros.exception;

public class LibroYaReservadoException extends RuntimeException {
    public LibroYaReservadoException(String mensaje) {
        super(mensaje);
    }
}