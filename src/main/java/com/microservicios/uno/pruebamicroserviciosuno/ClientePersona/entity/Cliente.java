package com.microservicios.uno.pruebamicroserviciosuno.ClientePersona.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservicios.uno.pruebamicroserviciosuno.CuentaMovimiento.entity.Cuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona {
    @Column(name = "cliente_id", unique = true)
    private String clienteId; // Clave única (PK) para Cliente
    private String contraseña;
    private boolean estado;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cuenta> listaCuentas = new ArrayList<>();
}
