package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.repository;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
