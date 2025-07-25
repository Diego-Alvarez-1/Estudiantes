package org.ebiz.msreservassalas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sala")
@Setter
@Getter
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "dias_anticipo_cancelacion")
    private Integer diasAnticipoCancelacion = 1;

    @Column(name = "habilitado")
    private Integer habilitado = 1;

    @OneToMany(mappedBy = "sala")
    @JsonManagedReference("sala-reservas")
    private List<Reserva> reservas;
}