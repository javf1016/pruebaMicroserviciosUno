package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.service;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Cliente;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.ClienteNotFoundException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomDataIntegrityException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomServiceException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.repository.ClienteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public Cliente crearCliente(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al crear el cliente: los datos son inválidos.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al crear el cliente.", ex);
        }
    }

    @Override
    public Cliente obtenerClientePorId(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public Cliente actualizarCliente(Long clienteId, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));
        clienteExistente.setContraseña(cliente.getNombre());
        clienteExistente.setGenero(cliente.getGenero());
        clienteExistente.setEdad(cliente.getEdad());
        try {
            return clienteRepository.save(clienteExistente);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al actualizar el cliente: los datos son inválidos.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al actualizar el cliente.", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = { CustomServiceException.class, ClienteNotFoundException.class })
    public void eliminarCliente(Long clienteId) {
        if (clienteRepository.existsById(clienteId)) {
            try {
                clienteRepository.deleteById(clienteId);
            } catch (DataIntegrityViolationException ex) {
                throw new CustomDataIntegrityException("Error al eliminar el cliente: los datos son referenciados por otras entidades.");
            } catch (Exception ex) {
                throw new CustomServiceException("Error al eliminar el cliente.", ex);
            }
        } else {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId);
        }
    }
}