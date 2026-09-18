package com.ciperiani.tp2.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ciperiani.tp2.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT DISTINCT p FROM Pedido p " +
           "JOIN p.cliente c " +
           "JOIN p.detalles d " +
           "JOIN d.producto pr " +
           "WHERE (:clienteId IS NULL OR c.id = :clienteId) " +
           "AND (:categoria IS NULL OR pr.categoria = :categoria) " +
           "AND (:fechaDesde IS NULL OR p.fechaPedido >= :fechaDesde) " +
           "AND (:fechaHasta IS NULL OR p.fechaPedido <= :fechaHasta) " +
           "AND (:estado IS NULL OR p.estado = :estado)")
    List<Pedido> buscarConFiltros(
            @Param("clienteId") Long clienteId,
            @Param("categoria") String categoria,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("estado") String estado
    );
}