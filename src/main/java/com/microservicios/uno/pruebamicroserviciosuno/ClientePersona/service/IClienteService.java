package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.service;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Cliente;

import java.util.List;

public interface IClienteService {
    Cliente crearCliente(Cliente cliente);
    Cliente obtenerClientePorId(Long clienteId);
    List<Cliente> obtenerTodosLosClientes();
    Cliente actualizarCliente(Long clienteId, Cliente cliente);
    void eliminarCliente(Long clienteId);
}
