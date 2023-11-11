package com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity.Cliente;
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
@Table(name = "cuenta")
public class Cuenta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroCuenta;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    })
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Movimiento> listaMovimientos = new ArrayList<>();
}
