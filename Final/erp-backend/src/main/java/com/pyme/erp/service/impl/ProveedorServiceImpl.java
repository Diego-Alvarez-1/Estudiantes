package com.pyme.erp.service.impl;

import com.pyme.erp.dto.request.ProveedorRequest;
import com.pyme.erp.exception.BusinessException;
import com.pyme.erp.exception.ErrorCode;
import com.pyme.erp.model.Proveedor;
import com.pyme.erp.repository.ProveedorRepository;
import com.pyme.erp.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public Proveedor crear(ProveedorRequest request) {
        log.debug("Creando nuevo proveedor con RUT: {}", request.getRut());
        
        // Validar que no exista proveedor con mismo email
        Optional<Proveedor> proveedorPorEmail = proveedorRepository.findByEmail(request.getEmail());
        if (proveedorPorEmail.isPresent()) {
            throw new BusinessException(ErrorCode.PROVEEDOR_EMAIL_DUPLICADO);
        }
        
        // Validar que no exista proveedor con mismo RUT
        Optional<Proveedor> proveedorPorRut = proveedorRepository.findByRut(request.getRut());
        if (proveedorPorRut.isPresent()) {
            throw new BusinessException(ErrorCode.PROVEEDOR_RUT_DUPLICADO);
        }
        
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(request.getNombre());
        proveedor.setEmail(request.getEmail());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setRut(request.getRut());
        
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor actualizar(Long id, ProveedorRequest request) {
        log.debug("Actualizando proveedor con ID: {}", id);
        
        Proveedor proveedor = obtenerPorId(id);
        
        // Validar email duplicado solo si cambió
        if (!proveedor.getEmail().equals(request.getEmail())) {
            Optional<Proveedor> proveedorPorEmail = proveedorRepository.findByEmail(request.getEmail());
            if (proveedorPorEmail.isPresent()) {
                throw new BusinessException(ErrorCode.PROVEEDOR_EMAIL_DUPLICADO);
            }
        }
        
        // Validar RUT duplicado solo si cambió
        if (!proveedor.getRut().equals(request.getRut())) {
            Optional<Proveedor> proveedorPorRut = proveedorRepository.findByRut(request.getRut());
            if (proveedorPorRut.isPresent()) {
                throw new BusinessException(ErrorCode.PROVEEDOR_RUT_DUPLICADO);
            }
        }
        
        proveedor.setNombre(request.getNombre());
        proveedor.setEmail(request.getEmail());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setRut(request.getRut());
        
        return proveedorRepository.save(proveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVEEDOR_NO_ENCONTRADO));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> listarActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    @Override
    public void desactivar(Long id) {
        log.debug("Desactivando proveedor con ID: {}", id);
        Proveedor proveedor = obtenerPorId(id);
        proveedor.setActivo(false);
        proveedorRepository.save(proveedor);
    }

    @Override
    public void activar(Long id) {
        log.debug("Activando proveedor con ID: {}", id);
        Proveedor proveedor = obtenerPorId(id);
        proveedor.setActivo(true);
        proveedorRepository.save(proveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> buscarPorNombre(String nombre) {
        return proveedorRepository.buscarPorNombre(nombre);
    }
}