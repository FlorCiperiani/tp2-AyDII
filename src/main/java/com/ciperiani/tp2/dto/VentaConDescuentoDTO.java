package com.ciperiani.tp2.dto;

public class VentaConDescuentoDTO {

    private String producto;
    private int cantidad;
    private double precioUnitario;
    private double montoConDescuento;

    public VentaConDescuentoDTO() {
    }

    public VentaConDescuentoDTO(String producto, int cantidad,
                                double precioUnitario,
                                double montoConDescuento) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.montoConDescuento = montoConDescuento;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getMontoConDescuento() {
        return montoConDescuento;
    }

    public void setMontoConDescuento(double montoConDescuento) {
        this.montoConDescuento = montoConDescuento;
    }
}