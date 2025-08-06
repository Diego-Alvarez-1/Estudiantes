package com.pyme.erp.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompraRequest {
    
    @NotNull(message = "El proveedor es obligatorio")
    private Long proveedorId;
    
    @NotEmpty(message = "Debe incluir al menos un detalle de compra")
    @Valid
    private List<DetalleCompraRequest> detalles;
}