package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.service;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Persona;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomDataIntegrityException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomServiceException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.PersonaNotFoundException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.repository.PersonaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaServiceImpl implements IPersonaService {

    private final PersonaRepository personaRepository;
    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public Persona crearPersona(Persona persona) {
        try {
            return personaRepository.save(persona);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al crear la persona: los datos son inválidos.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al crear la persona.", ex);
        }
    }

    @Override
    public Persona obtenerPersonaPorId(Long personaId) {
        return personaRepository.findById(personaId)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con ID: " + personaId));
    }

    @Override
    public List<Persona> obtenerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public Persona actualizarPersona(Long personaId, Persona persona) {
        Persona personaExistente = personaRepository.findById(personaId)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con ID: " + personaId));
        //Si se usara persona independiente se modificarian aca
        try {
            return personaRepository.save(personaExistente);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al actualizar la persona: los datos son inválidos.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al actualizar la persona.", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = { CustomServiceException.class, PersonaNotFoundException.class })
    public void eliminarPersona(Long personaId) {
        if (personaRepository.existsById(personaId)) {
            try {
                personaRepository.deleteById(personaId);
            } catch (DataIntegrityViolationException ex) {
                throw new CustomDataIntegrityException("Error al eliminar la persona: los datos son referenciados por otras entidades.");
            } catch (Exception ex) {
                throw new CustomServiceException("Error al eliminar la persona.", ex);
            }
        } else {
            throw new PersonaNotFoundException("Persona no encontrada con ID: " + personaId);
        }
    }
}