package com.ciperiani.tp2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciperiani.tp2.dto.ClienteRequestDTO;
import com.ciperiani.tp2.exception.ApiResponse;
import com.ciperiani.tp2.model.Cliente;
import com.ciperiani.tp2.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Alta simple de cliente", description = "Registra un cliente en la base de datos sin validaciones estrictas en campos.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Cliente creado con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> altaSimple(@RequestBody ClienteRequestDTO dto) {
        // Alta simple (Endpoint 1): No valida email duplicado previo según requerimiento de alta simple, o se puede incluir si se desea.
        Cliente clienteCreado = clienteService.registrarCliente(dto, false);
        ApiResponse<Cliente> respuesta = new ApiResponse<>(201, "Cliente creado con éxito", clienteCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Alta de cliente con validación", description = "Registra un cliente aplicando Bean Validation y verificando que el email no esté duplicado.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Cliente creado con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos o email ya registrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/validado")
    public ResponseEntity<ApiResponse<Cliente>> altaValidado(@Valid @RequestBody ClienteRequestDTO dto) {
        // Alta con validación (Endpoint 2): Valida campos y email único en BD
        Cliente clienteCreado = clienteService.registrarCliente(dto, true);
        ApiResponse<Cliente> respuesta = new ApiResponse<>(201, "Cliente creado con éxito", clienteCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}