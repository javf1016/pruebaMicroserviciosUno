package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimiento")
public class Movimiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "numeroCuenta")
    private Cuenta cuenta;
}