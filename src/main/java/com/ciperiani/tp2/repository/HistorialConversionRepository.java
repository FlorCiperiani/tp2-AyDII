package com.ciperiani.tp2.repository;

import com.ciperiani.tp2.model.HistorialConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Integer> {
    
    // Busca por origen y destino, ordenado de la más reciente a la más antigua
    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(String monedaOrigen, String monedaDestino);
}