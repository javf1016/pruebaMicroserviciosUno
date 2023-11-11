package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.controller;

import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Cuenta;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Movimiento;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private ICuentaService cuentaService;

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta, @RequestParam Long clienteId) {
        Cuenta nuevaCuenta = cuentaService.crearCuenta(cuenta, clienteId);
        return new ResponseEntity<>(nuevaCuenta, HttpStatus.CREATED);
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Long numeroCuenta) {
        Cuenta cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodasLasCuentas() {
        List<Cuenta> cuentas = cuentaService.obtenerTodasLasCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long numeroCuenta, @RequestBody Cuenta cuenta) {
        Cuenta cuentaActualizada = cuentaService.actualizarCuenta(numeroCuenta, cuenta);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long numeroCuenta) {
        cuentaService.eliminarCuenta(numeroCuenta);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{numeroCuenta}/movimientos")
    public ResponseEntity<Void> registrarMovimiento(@PathVariable Long numeroCuenta, @RequestBody Movimiento movimiento) {
        cuentaService.registrarMovimiento(numeroCuenta, movimiento);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
