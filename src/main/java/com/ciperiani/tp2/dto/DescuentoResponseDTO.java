package com.ciperiani.tp2.dto;

import java.util.List;

public class DescuentoResponseDTO {

    private List<VentaConDescuentoDTO> ventas;
    private double totalConDescuento;

    public DescuentoResponseDTO() {
    }

    public DescuentoResponseDTO(List<VentaConDescuentoDTO> ventas,
                                double totalConDescuento) {
        this.ventas = ventas;
        this.totalConDescuento = totalConDescuento;
    }

    public List<VentaConDescuentoDTO> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaConDescuentoDTO> ventas) {
        this.ventas = ventas;
    }

    public double getTotalConDescuento() {
        return totalConDescuento;
    }

    public void setTotalConDescuento(double totalConDescuento) {
        this.totalConDescuento = totalConDescuento;
    }
}