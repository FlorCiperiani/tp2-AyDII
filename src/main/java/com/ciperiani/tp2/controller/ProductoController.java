package com.ciperiani.tp2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ciperiani.tp2.dto.ProductoRequestDTO;
import com.ciperiani.tp2.exception.ApiResponse;
import com.ciperiani.tp2.model.Producto;
import com.ciperiani.tp2.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catalogo")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve el listado completo de productos en memoria.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Productos obtenidos con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operación realizada con éxito", productos);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar y filtrar productos", description = "Filtra productos opcionalmente por categoría, precio mínimo y precio máximo.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Producto>>> buscarProductos(
            @Parameter(description = "Categoría del producto", example = "Periféricos") @RequestParam(required = false) String categoria,
            @Parameter(description = "Precio mínimo", example = "1000") @RequestParam(required = false) Double precioMin,
            @Parameter(description = "Precio máximo", example = "10000") @RequestParam(required = false) Double precioMax) {

        List<Producto> resultado = productoService.buscarProductos(categoria, precioMin, precioMax);
        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operación realizada con éxito", resultado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Ordenar productos", description = "Ordena el catálogo según un criterio (precio o nombre) y orden (asc o desc).")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Productos ordenados con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/ordenar")
    public ResponseEntity<ApiResponse<List<Producto>>> ordenarProductos(
            @Parameter(description = "Criterio de ordenamiento ('precio' o 'nombre')", example = "precio") @RequestParam(defaultValue = "precio") String criterio,
            @Parameter(description = "Dirección del orden ('asc' o 'desc')", example = "desc") @RequestParam(defaultValue = "asc") String orden) {

        List<Producto> resultado = productoService.ordenarProductos(criterio, orden);
        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operación realizada con éxito", resultado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Agregar un nuevo producto", description = "Recibe un producto por JSON y lo añade a la memoria.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Producto creado con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos en la petición"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> agregarProducto(@Valid @RequestBody ProductoRequestDTO dto) {
        Producto nuevo = productoService.agregarProducto(dto);
        ApiResponse<Producto> respuesta = new ApiResponse<>(201, "Producto creado con éxito", nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Modificar stock de un producto", description = "Modifica sumando o restando unidades al stock actual mediante ID.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stock modificado con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Stock insuficiente o datos inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Producto>> modificarStock(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Long id,
            @Parameter(description = "Cantidad a sumar o restar", example = "5") @RequestParam Integer cantidad) {

        Producto actualizado = productoService.modificarStock(id, cantidad);
        ApiResponse<Producto> respuesta = new ApiResponse<>(200, "Stock modificado con éxito", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del catálogo por su ID.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto eliminado con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar", example = "1") @PathVariable Long id) {

        productoService.eliminarProducto(id);
        ApiResponse<Void> respuesta = new ApiResponse<>(200, "Producto eliminado con éxito", null);
        return ResponseEntity.ok(respuesta);
    }
}