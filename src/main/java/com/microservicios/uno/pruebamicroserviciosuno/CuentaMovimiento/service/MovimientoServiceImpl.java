package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.service;

import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CuentaNotFoundException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomDataIntegrityException;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.exception.CustomServiceException;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Cuenta;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Movimiento;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.exception.SaldoInsuficienteException;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.repository.CuentaRepository;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.repository.MovimientoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

    private final MovimientoRepository movimientoRepository;

    private final CuentaRepository cuentaRepository;

    public MovimientoServiceImpl(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    @Transactional(rollbackFor = CustomServiceException.class)
    public Movimiento crearMovimiento(Movimiento movimiento, Long numeroCuenta) {
        try {
            Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                    .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
            movimiento.setCuenta(cuenta);
            movimiento.setFecha(LocalDateTime.now().toString());
            Movimiento nuevoMovimiento = movimientoRepository.save(movimiento);

            // Actualiza el saldo de la cuenta según el tipo de movimiento (depósito o retiro)
            if (movimiento.getTipoMovimiento().equals("DEPOSITO")) {
                cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimiento.getValor());
            } else if (movimiento.getTipoMovimiento().equals("RETIRO")) {
                cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimiento.getValor());
                if (cuenta.getSaldoInicial() < 0) {
                    throw new SaldoInsuficienteException("Saldo no disponible");
                }
            }
            cuentaRepository.save(cuenta);

            return nuevoMovimiento;
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityException("Error al crear el movimiento: los datos son inválidos.");
        }
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuenta(Long numeroCuenta) {
        return movimientoRepository.findByCuentaNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Movimiento> generarReporte(String fechaInicio, String fechaFin, Long clienteId) {
        try {
            return movimientoRepository.findMovimientosByFechaAndCliente(fechaInicio, fechaFin, clienteId);
        } catch (Exception ex) {
            throw new CustomServiceException("Error al generar el informe de movimientos.", ex);
        }
    }
}
