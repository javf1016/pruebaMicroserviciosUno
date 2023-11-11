package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.repository;

import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    // Buscar movimientos por número de cuenta
    List<Movimiento> findByCuentaNumeroCuenta(Long numeroCuenta);

    // Buscar movimientos por fecha y cliente utilizando una consulta personalizada
    @Query("SELECT m FROM Movimiento m WHERE m.fecha BETWEEN :fechaInicio AND :fechaFin AND m.cuenta.cliente.clienteId = :clienteId")
    List<Movimiento> findMovimientosByFechaAndCliente(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin, @Param("clienteId") Long clienteId);
}