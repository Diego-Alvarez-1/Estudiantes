package com.pyme.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_venta", schema = "erp")
@Setter
@Getter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long id;

    @Column(name = "numero_factura", unique = true, nullable = false)
    private String numeroFactura;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoVenta estado = EstadoVenta.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonIgnoreProperties("ventas")
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("venta")
    private List<DetalleVenta> detalles;

    @PrePersist
    protected void onCreate() {
        fechaVenta = LocalDateTime.now();
    }

    public enum EstadoVenta {
        PENDIENTE, PAGADA, CANCELADA
    }
}