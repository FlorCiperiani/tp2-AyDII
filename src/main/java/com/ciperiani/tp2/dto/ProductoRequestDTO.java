package com.ciperiani.tp2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductoRequestDTO {

    @Schema(description = "Nombre del producto", example = "Teclado Mecánico")
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;

    @Schema(description = "Categoría del producto", example = "Periféricos")
    @NotBlank(message = "La categoría no puede estar vacía")
    private String categoria;

    @Schema(description = "Precio unitario (debe ser mayor a 0)", example = "25000.0")
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @Schema(description = "Stock disponible (debe ser mayor o igual a 0)", example = "10")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser mayor o igual que 0")
    private Integer stock;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}