package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception;

public class CuentaNotFoundException extends RuntimeException {
    public CuentaNotFoundException(String message) {
        super(message);
    }
}