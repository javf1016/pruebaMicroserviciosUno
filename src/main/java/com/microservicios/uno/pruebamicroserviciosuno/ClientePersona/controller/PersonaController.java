package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.controller;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Persona;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.service.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        Persona nuevaPersona = personaService.crearPersona(persona);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }

    @GetMapping("/{personaId}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable Long personaId) {
        Persona persona = personaService.obtenerPersonaPorId(personaId);
        return ResponseEntity.ok(persona);
    }

    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodasLasPersonas() {
        List<Persona> personas = personaService.obtenerTodasLasPersonas();
        return ResponseEntity.ok(personas);
    }

    @PutMapping("/{personaId}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long personaId, @RequestBody Persona persona) {
        Persona personaActualizada = personaService.actualizarPersona(personaId, persona);
        return ResponseEntity.ok(personaActualizada);
    }

    @DeleteMapping("/{personaId}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long personaId) {
        personaService.eliminarPersona(personaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
