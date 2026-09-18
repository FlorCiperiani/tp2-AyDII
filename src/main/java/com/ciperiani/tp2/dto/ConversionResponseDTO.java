package com.ciperiani.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConversionResponseDTO {
    private BigDecimal montoOriginal;
    private String monedaOrigen;
    private String monedaDestino;
    private BigDecimal tasaCambio;
    private BigDecimal montoConvertido;
    private LocalDate fecha;

    public ConversionResponseDTO(BigDecimal montoOriginal, String monedaOrigen, String monedaDestino, BigDecimal tasaCambio, BigDecimal montoConvertido, LocalDate fecha) {
        this.montoOriginal = montoOriginal;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.tasaCambio = tasaCambio;
        this.montoConvertido = montoConvertido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public BigDecimal getMontoOriginal() { return montoOriginal; }
    public void setMontoOriginal(BigDecimal montoOriginal) { this.montoOriginal = montoOriginal; }

    public String getMonedaOrigen() { return monedaOrigen; }
    public void setMonedaOrigen(String monedaOrigen) { this.monedaOrigen = monedaOrigen; }

    public String getMonedaDestino() { return monedaDestino; }
    public void setMonedaDestino(String monedaDestino) { this.monedaDestino = monedaDestino; }

    public BigDecimal getTasaCambio() { return tasaCambio; }
    public void setTasaCambio(BigDecimal tasaCambio) { this.tasaCambio = tasaCambio; }

    public BigDecimal getMontoConvertido() { return montoConvertido; }
    public void setMontoConvertido(BigDecimal montoConvertido) { this.montoConvertido = montoConvertido; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}