package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.service;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Cliente;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.ClienteNotFoundException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CuentaNotFoundException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomDataIntegrityException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomServiceException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.repository.ClienteRepository;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Cuenta;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Movimiento;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.repository.CuentaRepository;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.repository.MovimientoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CuentaServiceImpl implements ICuentaService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    private final ClienteRepository clienteRepository;
    public CuentaServiceImpl(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public Cuenta crearCuenta(Cuenta cuenta, Long clienteId) {
        try {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));
            cuenta.setCliente(cliente);
            return cuentaRepository.save(cuenta);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al crear la cuenta: los datos son inválidos.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al crear la cuenta.", ex);
        }
    }

    @Override
    public Cuenta obtenerCuentaPorNumero(Long numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
    }

    @Override
    public List<Cuenta> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public Cuenta actualizarCuenta(Long numeroCuenta, Cuenta cuenta) {
        Cuenta cuentaExistente = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
        cuentaExistente.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaExistente.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaExistente.setEstado(cuenta.isEstado());
        try {
            return cuentaRepository.save(cuentaExistente);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al actualizar la cuenta: los datos son inválidos.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al actualizar la cuenta.", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = { CustomServiceException.class, CuentaNotFoundException.class })
    public void eliminarCuenta(Long numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
        try {
            cuentaRepository.delete(cuenta);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al eliminar la cuenta: los datos son referenciados por otras entidades.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al eliminar la cuenta.", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public void registrarMovimiento(Long numeroCuenta, Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));

        try {
            movimiento.setCuenta(cuenta);
            movimiento.setFecha(LocalDateTime.now().toString());
            movimientoRepository.save(movimiento);

            // Actualiza el saldo de la cuenta según el tipo de movimiento (depósito o retiro)
            if (movimiento.getTipoMovimiento().equals("DEPOSITO")) {
                cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimiento.getValor());
            } else if (movimiento.getTipoMovimiento().equals("RETIRO")) {
                cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimiento.getValor());
            }

            cuentaRepository.save(cuenta);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al registrar el movimiento: los datos son inválidos.");
        } catch (Exception ex) {
            throw new CustomServiceException("Error al registrar el movimiento.", ex);
        }
    }
}
