package com.ciperiani.tp2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ciperiani.tp2.dto.DescuentoResponseDTO;
import com.ciperiani.tp2.dto.VentaDTO;
import com.ciperiani.tp2.dto.VentaEstadisticasDTO;
import com.ciperiani.tp2.exception.ApiResponse;
import com.ciperiani.tp2.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
@Validated
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @Operation(
            summary = "Calcular estadísticas de ventas",
            description = "Procesa una lista de ventas y calcula estadísticas."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Estadísticas calculadas correctamente"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @PostMapping("/estadisticas")
    public ResponseEntity<ApiResponse<VentaEstadisticasDTO>> calcularEstadisticas(
            @Valid @RequestBody List<@Valid VentaDTO> ventas) {

        if (ventas.isEmpty()) {
            throw new IllegalArgumentException(
                    "La lista de ventas no puede estar vacía"
            );
        }

        VentaEstadisticasDTO resultado =
                ventaService.calcularEstadisticas(ventas);

        ApiResponse<VentaEstadisticasDTO> respuesta =
                new ApiResponse<>(
                        200,
                        "Operación realizada con éxito",
                        resultado
                );

        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Aplicar descuento a las ventas",
            description = "Aplica un porcentaje de descuento a cada venta."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Descuento aplicado correctamente"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o porcentaje incorrecto"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @PostMapping("/aplicar-descuento")
    public ResponseEntity<ApiResponse<DescuentoResponseDTO>> aplicarDescuento(
            @Valid @RequestBody List<@Valid VentaDTO> ventas,

            @Parameter(
                    description = "Porcentaje de descuento entre 0 y 100",
                    example = "10"
            )
            @RequestParam double porcentaje) {

        if (ventas.isEmpty()) {
            throw new IllegalArgumentException(
                    "La lista de ventas no puede estar vacía"
            );
        }

        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException(
                    "El porcentaje de descuento debe estar entre 0 y 100"
            );
        }

        DescuentoResponseDTO resultado =
                ventaService.aplicarDescuento(
                        ventas,
                        porcentaje
                );

        ApiResponse<DescuentoResponseDTO> respuesta =
                new ApiResponse<>(
                        200,
                        "Descuento aplicado correctamente",
                        resultado
                );

        return ResponseEntity.ok(respuesta);
    }
}