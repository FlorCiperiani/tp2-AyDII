package com.ciperiani.tp2.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ciperiani.tp2.dto.ProductoRequestDTO;
import com.ciperiani.tp2.exception.RecursoNoEncontradoException;
import com.ciperiani.tp2.model.Producto;

@Service
public class ProductoService {

    private final List<Producto> catalogo = new ArrayList<>();
    private final AtomicLong contadorIds = new AtomicLong(1);

    public ProductoService() {
        // Carga inicial de al menos 8 productos de ejemplo
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Mouse Inalámbrico", "Periféricos", 4500.0, 15));
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Teclado Mecánico", "Periféricos", 25000.0, 10));
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Monitor 24\"", "Monitores", 85000.0, 5));
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Auriculares Gamer", "Audio", 18000.0, 8));
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Silla Gamer", "Muebles", 120000.0, 3));
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Notebook i5", "Equipos", 350000.0, 4));
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Pad Mouse XXL", "Periféricos", 3500.0, 25));
        catalogo.add(new Producto(contadorIds.getAndIncrement(), "Webcam HD", "Periféricos", 15000.0, 12));
    }

    public List<Producto> obtenerTodos() {
        return catalogo;
    }

    public List<Producto> buscarProductos(String categoria, Double precioMin, Double precioMax) {
        return catalogo.stream()
                .filter(p -> categoria == null || p.getCategoria().equalsIgnoreCase(categoria))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    public List<Producto> ordenarProductos(String criterio, String orden) {
        Comparator<Producto> comparador;

        if ("nombre".equalsIgnoreCase(criterio)) {
            comparador = Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER);
        } else {
            comparador = Comparator.comparing(Producto::getPrecio);
        }

        if ("desc".equalsIgnoreCase(orden)) {
            comparador = comparador.reversed();
        }

        return catalogo.stream()
                .sorted(comparador)
                .collect(Collectors.toList());
    }

    public Producto agregarProducto(ProductoRequestDTO dto) {
        Producto nuevoProducto = new Producto(
                contadorIds.getAndIncrement(),
                dto.getNombre(),
                dto.getCategoria(),
                dto.getPrecio(),
                dto.getStock()
        );
        catalogo.add(nuevoProducto);
        return nuevoProducto;
    }

    public Producto modificarStock(Long id, Integer cantidad) {
        Producto producto = buscarPorId(id);
        int nuevoStock = producto.getStock() + cantidad;

        if (nuevoStock < 0) {
            throw new IllegalArgumentException(
                "Stock insuficiente. Stock actual: " + producto.getStock() + 
                ", intentó restar: " + Math.abs(cantidad)
            );
        }

        producto.setStock(nuevoStock);
        return producto;
    }

    public void eliminarProducto(Long id) {
        Producto producto = buscarPorId(id);
        catalogo.remove(producto);
    }

    private Producto buscarPorId(Long id) {
        return catalogo.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró un producto con el ID: " + id));
    }
}