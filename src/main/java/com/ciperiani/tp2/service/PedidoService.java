package com.ciperiani.tp2.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ciperiani.tp2.dto.DetalleProductoDTO;
import com.ciperiani.tp2.dto.PedidoResponseDTO;
import com.ciperiani.tp2.model.Pedido;
import com.ciperiani.tp2.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponseDTO> buscarPedidos(Long clienteId, String categoria, LocalDate fechaDesde, LocalDate fechaHasta, String estado) {
        List<Pedido> pedidos = pedidoRepository.buscarConFiltros(clienteId, categoria, fechaDesde, fechaHasta, estado);

        return pedidos.stream().map(pedido -> {
            String nombreCliente = pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido();

            List<DetalleProductoDTO> detallesDTO = pedido.getDetalles().stream().map(det -> {
                double subtotal = det.getCantidad() * det.getPrecioUnitario().doubleValue();
                return new DetalleProductoDTO(
                        det.getProducto().getNombre(),
                        det.getProducto().getCategoria(), 
                        det.getCantidad(),
                        subtotal
                );
            }).collect(Collectors.toList());

            double totalPedido = detallesDTO.stream().mapToDouble(DetalleProductoDTO::getSubtotal).sum();

            return new PedidoResponseDTO(
                    pedido.getId(),
                    nombreCliente,
                    pedido.getFechaPedido(),
                    pedido.getEstado(),
                    totalPedido,
                    detallesDTO
            );
        }).collect(Collectors.toList());
    }
}