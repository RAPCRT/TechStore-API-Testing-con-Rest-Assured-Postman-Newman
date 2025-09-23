package com.techstore.api.service;

import com.techstore.api.model.Product;


public interface ServicioDescuento {
    // Retorna el descuento como fracción: 0.10 = 10%
    double obtenerDescuento(Product producto); //Obtiene el descuento de la clase Producto 
}