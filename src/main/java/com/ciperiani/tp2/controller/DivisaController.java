package com.ciperiani.tp2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ciperiani.tp2.dto.ConversionResponseDTO;
import com.ciperiani.tp2.dto.HistorialResponseDTO;
import com.ciperiani.tp2.exception.ApiResponse;
import com.ciperiani.tp2.service.DivisaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/divisas")
public class DivisaController {

    private final DivisaService divisaService;

    public DivisaController(DivisaService divisaService) {
        this.divisaService = divisaService;
    }

   @Operation(summary = "Convertir divisa y registrar", description = "Consulta la API de Frankfurter, realiza la conversión y guarda el registro en la base de datos.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Conversión realizada con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "502", description = "Error externo")
    })
    @PostMapping("/consultar")
    public ResponseEntity<ApiResponse<ConversionResponseDTO>> convertirDivisa(
            @Parameter(description = "Monto a convertir", example = "100") @RequestParam Double monto,
            @Parameter(description = "Moneda origen", example = "USD") @RequestParam String origen,
            @Parameter(description = "Moneda destino", example = "ARS") @RequestParam String destino) {

        ConversionResponseDTO resultado = divisaService.convertirDivisa(monto, origen, destino);
        ApiResponse<ConversionResponseDTO> respuesta = new ApiResponse<>(200, "Operación realizada con éxito", resultado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Historial de cotizaciones", description = "Recupera todas las consultas guardadas para un par de monedas ordenadas de más reciente a más antigua.")
    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<HistorialResponseDTO>>> obtenerHistorial(
            @Parameter(description = "Moneda origen", example = "USD") @RequestParam String origen,
            @Parameter(description = "Moneda destino", example = "ARS") @RequestParam String destino) {

        List<HistorialResponseDTO> historial = divisaService.obtenerHistorial(origen, destino);
        ApiResponse<List<HistorialResponseDTO>> respuesta = new ApiResponse<>(200, "Historial obtenido con éxito", historial);
        return ResponseEntity.ok(respuesta);
    }
}