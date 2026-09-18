package com.ciperiani.tp2.dto;

import java.time.LocalDate;

public class FrankfurterResponseDTO {
    private String base;
    private String quote;
    private Double rate;
    private LocalDate date;

    // Getters y Setters
    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getQuote() { return quote; }
    public void setQuote(String quote) { this.quote = quote; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}