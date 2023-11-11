package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.controller;

import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Movimiento;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.service.IMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private IMovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody Movimiento movimiento, @RequestParam Long numeroCuenta) {
        Movimiento nuevoMovimiento = movimientoService.crearMovimiento(movimiento, numeroCuenta);
        return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
    }

    @GetMapping("/cuenta/{numeroCuenta}")
    public ResponseEntity<List<Movimiento>> obtenerMovimientosPorCuenta(@PathVariable Long numeroCuenta) {
        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<Movimiento>> generarReporte(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam Long clienteId
    ) {
        List<Movimiento> movimientos = movimientoService.generarReporte(fechaInicio, fechaFin, clienteId);
        return ResponseEntity.ok(movimientos);
    }
}
