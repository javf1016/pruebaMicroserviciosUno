package com.microservicios.uno.pruebamicroserviciosuno;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Cliente;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.repository.ClienteRepository;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.service.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testActualizarCliente() {
        // Crear un cliente de prueba
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Nombre de prueba");
        cliente.setClienteId("cliente123");

        // Configurar el comportamiento simulado del repositorio
        Mockito.when(clienteRepository.findById(1L)).thenReturn(java.util.Optional.of(cliente));
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        // Realizar la operación que se está probando
        Cliente clienteActualizado = clienteService.actualizarCliente(1L, cliente);

        // Verificar que el cliente se haya actualizado correctamente
        Assertions.assertNotNull(clienteActualizado);
        Assertions.assertEquals("Nombre de prueba", clienteActualizado.getNombre());
        Assertions.assertEquals("cliente123", clienteActualizado.getClienteId());

        // Verificar las llamadas al repositorio
        Mockito.verify(clienteRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(clienteRepository, Mockito.times(1)).save(cliente);
    }
}
