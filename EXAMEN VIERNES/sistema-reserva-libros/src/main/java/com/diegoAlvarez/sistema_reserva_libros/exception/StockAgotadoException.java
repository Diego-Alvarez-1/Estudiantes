package com.diegoAlvarez.sistema_reserva_libros.exception;

public class StockAgotadoException extends RuntimeException {
    public StockAgotadoException(String mensaje) {
        super(mensaje);
    }
}