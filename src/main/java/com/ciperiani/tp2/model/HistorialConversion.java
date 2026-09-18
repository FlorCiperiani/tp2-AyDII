package com.ciperiani.tp2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_conversiones")
public class HistorialConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moneda_origen", nullable = false, length = 10)
    private String monedaOrigen;

    @Column(name = "moneda_destino", nullable = false, length = 10)
    private String monedaDestino;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(name = "monto_convertido", nullable = false)
    private BigDecimal montoConvertido;

    @Column(nullable = false)
    private BigDecimal tasa;

    @Column(name = "fecha_consulta", nullable = false)
    private LocalDateTime fechaConsulta;

    // Constructores
    public HistorialConversion() {}

    public HistorialConversion(String monedaOrigen, String monedaDestino, BigDecimal monto, BigDecimal montoConvertido, BigDecimal tasa, LocalDateTime fechaConsulta) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.monto = monto;
        this.montoConvertido = montoConvertido;
        this.tasa = tasa;
        this.fechaConsulta = fechaConsulta;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMonedaOrigen() { return monedaOrigen; }
    public void setMonedaOrigen(String monedaOrigen) { this.monedaOrigen = monedaOrigen; }

    public String getMonedaDestino() { return monedaDestino; }
    public void setMonedaDestino(String monedaDestino) { this.monedaDestino = monedaDestino; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public BigDecimal getMontoConvertido() { return montoConvertido; }
    public void setMontoConvertido(BigDecimal montoConvertido) { this.montoConvertido = montoConvertido; }

    public BigDecimal getTasa() { return tasa; }
    public void setTasa(BigDecimal tasa) { this.tasa = tasa; }

    public LocalDateTime getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDateTime fechaConsulta) { this.fechaConsulta = fechaConsulta; }
}