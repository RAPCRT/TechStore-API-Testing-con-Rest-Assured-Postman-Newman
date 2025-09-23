// src/test/java/com/techstore/model/Product.java
package com.techstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    public Long id;
    public String nombre;
    public String descripcion;
    public Double precio;
    public Integer stock;
    public Category category;

    // Mapea snake_case a campos Java (pueden ser String u OffsetDateTime)
    @JsonProperty("fecha_creacion")
    public String fechaCreacion;

    @JsonProperty("fecha_actualizacion")
    public String fechaActualizacion;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Category {
        public Long id;
        public String nombre;
        public String descripcion;
    }

}
