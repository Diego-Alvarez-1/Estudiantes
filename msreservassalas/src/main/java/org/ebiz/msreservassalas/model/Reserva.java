package org.ebiz.msreservassalas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Setter
@Getter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva;

    @Column(name = "cantidad_estudiantes")
    private Integer cantidadEstudiantes;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    private String estado = "ACTIVA";

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudiante_id")
    @JsonBackReference("estudiante-reservas")
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id")
    @JsonBackReference("sala-reservas")
    private Sala sala;
}