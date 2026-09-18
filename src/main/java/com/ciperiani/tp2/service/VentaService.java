package com.ciperiani.tp2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ciperiani.tp2.dto.DescuentoResponseDTO;
import com.ciperiani.tp2.dto.VentaConDescuentoDTO;
import com.ciperiani.tp2.dto.VentaConImporteDTO;
import com.ciperiani.tp2.dto.VentaDTO;
import com.ciperiani.tp2.dto.VentaEstadisticasDTO;

@Service
public class VentaService {

    public VentaEstadisticasDTO calcularEstadisticas(List<VentaDTO> ventas) {

        double totalFacturado = 0;

        VentaConImporteDTO ventaMayor = null;
        VentaConImporteDTO ventaMenor = null;

        Map<String, Integer> cantidadesPorProducto = new HashMap<>();

        for (VentaDTO venta : ventas) {

            double importe = venta.getCantidad() * venta.getPrecioUnitario();

            totalFacturado += importe;

            VentaConImporteDTO ventaConImporte =
                    new VentaConImporteDTO(
                            venta.getProducto(),
                            venta.getCantidad(),
                            venta.getPrecioUnitario(),
                            importe
                    );

            if (ventaMayor == null || importe > ventaMayor.getImporte()) {
                ventaMayor = ventaConImporte;
            }

            if (ventaMenor == null || importe < ventaMenor.getImporte()) {
                ventaMenor = ventaConImporte;
            }

            cantidadesPorProducto.put(
                    venta.getProducto(),
                    cantidadesPorProducto.getOrDefault(venta.getProducto(), 0)
                            + venta.getCantidad()
            );
        }

        String productoMasVendido = null;
        int mayorCantidad = 0;

        for (Map.Entry<String, Integer> entrada : cantidadesPorProducto.entrySet()) {

            if (entrada.getValue() > mayorCantidad) {
                mayorCantidad = entrada.getValue();
                productoMasVendido = entrada.getKey();
            }
        }

        int cantidadVentas = ventas.size();

        double ticketPromedio = totalFacturado / cantidadVentas;

        VentaEstadisticasDTO resultado = new VentaEstadisticasDTO();

        resultado.setTotalFacturado(totalFacturado);
        resultado.setCantidadVentas(cantidadVentas);
        resultado.setTicketPromedio(ticketPromedio);
        resultado.setVentaMayor(ventaMayor);
        resultado.setVentaMenor(ventaMenor);
        resultado.setProductoMasVendido(productoMasVendido);

        return resultado;
    }

    public DescuentoResponseDTO aplicarDescuento(
            List<VentaDTO> ventas,
            double porcentaje) {

        List<VentaConDescuentoDTO> ventasConDescuento = new ArrayList<>();

        double totalConDescuento = 0;

        for (VentaDTO venta : ventas) {

            double importe =
                    venta.getCantidad() * venta.getPrecioUnitario();

            double montoConDescuento =
                    importe * (1 - porcentaje / 100);

            totalConDescuento += montoConDescuento;

            VentaConDescuentoDTO ventaConDescuento =
                    new VentaConDescuentoDTO(
                            venta.getProducto(),
                            venta.getCantidad(),
                            venta.getPrecioUnitario(),
                            montoConDescuento
                    );

            ventasConDescuento.add(ventaConDescuento);
        }

        return new DescuentoResponseDTO(
                ventasConDescuento,
                totalConDescuento
        );
    }
}