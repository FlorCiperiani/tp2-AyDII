package com.ciperiani.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialResponseDTO {
    private LocalDateTime fecha;
    private BigDecimal tasaCambio;

    public HistorialResponseDTO(LocalDateTime fecha, BigDecimal tasaCambio) {
        this.fecha = fecha;
        this.tasaCambio = tasaCambio;
    }

    // Getters y Setters
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public BigDecimal getTasaCambio() { return tasaCambio; }
    public void setTasaCambio(BigDecimal tasaCambio) { this.tasaCambio = tasaCambio; }
}