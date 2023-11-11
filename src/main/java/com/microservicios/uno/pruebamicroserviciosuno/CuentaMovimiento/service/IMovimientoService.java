package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.service;

import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Movimiento;

import java.util.List;

public interface IMovimientoService {
    Movimiento crearMovimiento(Movimiento movimiento, Long numeroCuenta);
    List<Movimiento> obtenerMovimientosPorCuenta(Long numeroCuenta);
    List<Movimiento> generarReporte(String fechaInicio, String fechaFin, Long clienteId);
}
