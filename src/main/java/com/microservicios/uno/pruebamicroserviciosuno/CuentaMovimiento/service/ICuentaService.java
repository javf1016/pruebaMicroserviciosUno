package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.service;

import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Cuenta;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Movimiento;

import java.util.List;

public interface ICuentaService {
    Cuenta crearCuenta(Cuenta cuenta, Long clienteId);
    Cuenta obtenerCuentaPorNumero(Long numeroCuenta);
    List<Cuenta> obtenerTodasLasCuentas();
    Cuenta actualizarCuenta(Long numeroCuenta, Cuenta cuenta);
    void eliminarCuenta(Long numeroCuenta);
    void registrarMovimiento(Long numeroCuenta, Movimiento movimiento);
}
