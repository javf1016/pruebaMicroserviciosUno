package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.repository;

import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);
}
