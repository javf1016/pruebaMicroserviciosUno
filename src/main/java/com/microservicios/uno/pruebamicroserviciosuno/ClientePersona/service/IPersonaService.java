package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.service;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Persona;

import java.util.List;

public interface IPersonaService {
    Persona crearPersona(Persona persona);
    Persona obtenerPersonaPorId(Long personaId);
    List<Persona> obtenerTodasLasPersonas();
    Persona actualizarPersona(Long personaId, Persona persona);
    void eliminarPersona(Long personaId);
}