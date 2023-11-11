package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}