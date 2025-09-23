package com.techstore.api.service;

import com.techstore.api.model.Product;

import java.util.List;
import java.util.Objects;

public class GestorProductos {

    private final ServicioDescuento servicioDescuento;

    public GestorProductos(ServicioDescuento servicioDescuento) {
        this.servicioDescuento = Objects.requireNonNull(servicioDescuento);
    }

    // Aplica el descuento sobre el precio de cada producto y retorna la lista
    public List<Product> aplicarDescuentos(List<Product> productos) {
        if (productos == null) return List.of();
        for (Product p : productos) {
            Double precio = p.getPrecio();
            if (precio != null && precio > 0) {
                double d = servicioDescuento.obtenerDescuento(p); // e.g., 0.10, como es un tipo doble para que abarque decimales
                p.setPrecio(precio * (1.0 - d));
            }
        }
        return productos;
    }
}
