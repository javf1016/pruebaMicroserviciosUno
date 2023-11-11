package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception;

public class CustomDataIntegrityException extends RuntimeException {
    public CustomDataIntegrityException(String message) {
        super(message);
    }
}