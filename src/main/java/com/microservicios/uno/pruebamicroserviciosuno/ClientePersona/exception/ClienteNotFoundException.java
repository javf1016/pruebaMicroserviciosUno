package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message) {
        super(message);
    }
}