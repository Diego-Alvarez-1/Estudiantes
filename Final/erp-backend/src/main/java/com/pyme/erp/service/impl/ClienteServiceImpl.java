package com.pyme.erp.service.impl;

import com.pyme.erp.dto.request.ClienteRequest;
import com.pyme.erp.exception.BusinessException;
import com.pyme.erp.exception.ErrorCode;
import com.pyme.erp.model.Cliente;
import com.pyme.erp.repository.ClienteRepository;
import com.pyme.erp.service.ClienteService;
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
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente crear(ClienteRequest request) {
        log.debug("Creando nuevo cliente con RUT: {}", request.getRut());
        
        // Validar que no exista cliente con mismo email
        Optional<Cliente> clientePorEmail = clienteRepository.findByEmail(request.getEmail());
        if (clientePorEmail.isPresent()) {
            throw new BusinessException(ErrorCode.CLIENTE_EMAIL_DUPLICADO);
        }
        
        // Validar que no exista cliente con mismo RUT
        Optional<Cliente> clientePorRut = clienteRepository.findByRut(request.getRut());
        if (clientePorRut.isPresent()) {
            throw new BusinessException(ErrorCode.CLIENTE_RUT_DUPLICADO);
        }
        
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        cliente.setRut(request.getRut());
        
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizar(Long id, ClienteRequest request) {
        log.debug("Actualizando cliente con ID: {}", id);
        
        Cliente cliente = obtenerPorId(id);
        
        // Validar email duplicado solo si cambio
        if (!cliente.getEmail().equals(request.getEmail())) {
            Optional<Cliente> clientePorEmail = clienteRepository.findByEmail(request.getEmail());
            if (clientePorEmail.isPresent()) {
                throw new BusinessException(ErrorCode.CLIENTE_EMAIL_DUPLICADO);
            }
        }
        
        // Validar RUT duplicado solo si cambio
        if (!cliente.getRut().equals(request.getRut())) {
            Optional<Cliente> clientePorRut = clienteRepository.findByRut(request.getRut());
            if (clientePorRut.isPresent()) {
                throw new BusinessException(ErrorCode.CLIENTE_RUT_DUPLICADO);
            }
        }
        
        cliente.setNombre(request.getNombre());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        cliente.setRut(request.getRut());
        
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLIENTE_NO_ENCONTRADO));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarActivos() {
        return clienteRepository.findByActivoTrue();
    }

    @Override
    public void desactivar(Long id) {
        log.debug("Desactivando cliente con ID: {}", id);
        Cliente cliente = obtenerPorId(id);
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    @Override
    public void activar(Long id) {
        log.debug("Activando cliente con ID: {}", id);
        Cliente cliente = obtenerPorId(id);
        cliente.setActivo(true);
        clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.buscarPorNombre(nombre);
    }
}