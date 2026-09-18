package com.ciperiani.tp2.dto;

public class VentaConImporteDTO {

    private String producto;
    private int cantidad;
    private double precioUnitario;
    private double importe;

    public VentaConImporteDTO() {
    }

    public VentaConImporteDTO(String producto, int cantidad,
                              double precioUnitario, double importe) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.importe = importe;
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

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}