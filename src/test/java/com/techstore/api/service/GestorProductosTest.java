package com.techstore.api.service;

import com.techstore.api.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorProductosTest {

    @Test
    void aplicaReglaDeDescuentosYVerificaMock() {
        // Mock del servicio externo
        ServicioDescuento servicio = mock(ServicioDescuento.class);

        // Requerimiento enunciado, 10% si precio > 100; 0% en caso contrario
        when(servicio.obtenerDescuento(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            Double precio = p.getPrecio();
            return (precio != null && precio > 100.0) ? 0.10 : 0.0;
        });

        GestorProductos gestor = new GestorProductos(servicio);

        // Datos de prueba
        Product caro = new Product();
        caro.setNombre("Notebook");
        caro.setPrecio(150.0);

        Product barato = new Product();
        barato.setNombre("Lápiz");
        barato.setPrecio(2.0);

        // Ejecutar
        List<Product> salida = gestor.aplicarDescuentos(List.of(caro, barato));

        // Validaciones (AssertJ)
        assertThat(salida).hasSize(2);
        assertThat(caro.getPrecio()).isCloseTo(135.0, within(1e-9)); // 150 * 0.9
        assertThat(barato.getPrecio()).isCloseTo(2.0, within(1e-9)); // sin descuento

        // Verificaciones del mock
        verify(servicio, times(1)).obtenerDescuento(caro);
        verify(servicio, times(1)).obtenerDescuento(barato);
        verifyNoMoreInteractions(servicio);
    }
}
