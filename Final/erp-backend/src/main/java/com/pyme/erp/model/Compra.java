package com.pyme.erp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_compra", schema = "erp")
@Setter
@Getter
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long id;

    @Column(name = "numero_orden", unique = true, nullable = false)
    private String numeroOrden;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCompra estado = EstadoCompra.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    @JsonIgnoreProperties("compras")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("compra")
    private List<DetalleCompra> detalles;

    @PrePersist
    protected void onCreate() {
        fechaCompra = LocalDateTime.now();
    }

    public enum EstadoCompra {
        PENDIENTE, RECIBIDA, CANCELADA
    }
}