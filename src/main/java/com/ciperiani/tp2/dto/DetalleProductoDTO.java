package com.ciperiani.tp2.dto;

public class DetalleProductoDTO {
    private String nombre;
    private String categoria;
    private Integer cantidad;
    private Double subtotal;

    public DetalleProductoDTO(String nombre, String categoria, Integer cantidad, Double subtotal) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}