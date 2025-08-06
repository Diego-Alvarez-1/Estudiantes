package com.pyme.erp.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    
    // Errores de Cliente
    CLIENTE_NO_ENCONTRADO("CLI_001", "Cliente no encontrado"),
    CLIENTE_EMAIL_DUPLICADO("CLI_002", "Ya existe un cliente con este email"),
    CLIENTE_RUT_DUPLICADO("CLI_003", "Ya existe un cliente con este RUT"),
    
    // Errores de Proveedor
    PROVEEDOR_NO_ENCONTRADO("PRO_001", "Proveedor no encontrado"),
    PROVEEDOR_EMAIL_DUPLICADO("PRO_002", "Ya existe un proveedor con este email"),
    PROVEEDOR_RUT_DUPLICADO("PRO_003", "Ya existe un proveedor con este RUT"),
    
    // Errores de Producto
    PRODUCTO_NO_ENCONTRADO("PROD_001", "Producto no encontrado"),
    PRODUCTO_CODIGO_DUPLICADO("PROD_002", "Ya existe un producto con este código"),
    STOCK_INSUFICIENTE("PROD_003", "Stock insuficiente para realizar la operación"),
    
    // Errores de Venta
    VENTA_NO_ENCONTRADA("VEN_001", "Venta no encontrada"),
    VENTA_YA_CANCELADA("VEN_002", "La venta ya se encuentra cancelada"),
    
    // Errores de Compra
    COMPRA_NO_ENCONTRADA("COM_001", "Compra no encontrada"),
    COMPRA_YA_CANCELADA("COM_002", "La compra ya se encuentra cancelada"),
    
    // Errores generales
    OPERACION_NO_PERMITIDA("GEN_001", "Operación no permitida"),
    DATOS_INVALIDOS("GEN_002", "Los datos proporcionados no son válidos");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}