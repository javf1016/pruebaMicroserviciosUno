package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception;

public class PersonaNotFoundException extends RuntimeException {
    public PersonaNotFoundException(String message) {
        super(message);
    }
}