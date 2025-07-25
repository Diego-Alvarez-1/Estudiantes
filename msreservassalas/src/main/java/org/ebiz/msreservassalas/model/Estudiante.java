package org.ebiz.msreservassalas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estudiante")
@Setter
@Getter
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "carrera")
    private String carrera;

    @Column(name = "correo", unique = true)
    private String correo;

    @Column(name = "habilitado")
    private Integer habilitado = 1;
}