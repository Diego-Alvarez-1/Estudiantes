package com.pyme.erp.controller;

import com.pyme.erp.dto.request.VentaRequest;
import com.pyme.erp.dto.response.ResponseWrapper;
import com.pyme.erp.model.Venta;
import com.pyme.erp.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    @