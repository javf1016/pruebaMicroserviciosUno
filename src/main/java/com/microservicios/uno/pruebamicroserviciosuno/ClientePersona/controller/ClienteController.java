package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.controller;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Cliente;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.crearCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long clienteId) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long clienteId, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.actualizarCliente(clienteId, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long clienteId) {
        clienteService.eliminarCliente(clienteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
