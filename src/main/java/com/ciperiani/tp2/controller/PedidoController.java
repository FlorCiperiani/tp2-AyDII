package com.ciperiani.tp2.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ciperiani.tp2.dto.PedidoResponseDTO;
import com.ciperiani.tp2.exception.ApiResponse;
import com.ciperiani.tp2.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Consulta de pedidos con filtros combinables", description = "Permite buscar historial de pedidos aplicando filtros opcionales de cliente, categoría, rango de fechas y estado.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPedidos(
            @Parameter(description = "ID del cliente") @RequestParam(required = false) Long clienteId,
            @Parameter(description = "Nombre de la categoría del producto") @RequestParam(required = false) String categoria,
            @Parameter(description = "Fecha inicial (yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @Parameter(description = "Fecha final (yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @Parameter(description = "Estado del pedido (PENDIENTE, ENVIADO, ENTREGADO, CANCELADO)") @RequestParam(required = false) String estado) {

        List<PedidoResponseDTO> resultados = pedidoService.buscarPedidos(clienteId, categoria, fechaDesde, fechaHasta, estado);

        ApiResponse<List<PedidoResponseDTO>> respuesta = new ApiResponse<>(
                200,
                "Consulta realizada correctamente",
                resultados
        );

        return ResponseEntity.ok(respuesta);
    }
}