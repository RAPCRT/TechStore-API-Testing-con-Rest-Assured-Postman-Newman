package com.techstore.api.controller;

import com.techstore.api.exception.ResourceNotFoundException;
import com.techstore.api.model.Category;
import com.techstore.api.model.Product;
import com.techstore.api.repository.CategoryRepository;
import com.techstore.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Map<String, Object> payload) {
        Product product = new Product();
        product.setNombre((String) payload.get("nombre"));
        product.setDescripcion((String) payload.get("descripcion"));
        product.setPrecio(((Number) payload.get("precio")).doubleValue());
        product.setStock((Integer) payload.get("stock"));
        
        Long categoryId = ((Number) payload.get("id_categoria")).longValue();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con el ID '" + categoryId + "' no fue encontrada."));
        product.setCategory(category);

        Product newProduct = productRepository.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el ID '" + productId + "' no fue encontrado."));
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId,
                                                 @RequestBody Map<String, Object> payload) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el ID '" + productId + "' no fue encontrado."));

        product.setNombre((String) payload.get("nombre"));
        product.setDescripcion((String) payload.get("descripcion"));
        product.setPrecio(((Number) payload.get("precio")).doubleValue());
        product.setStock((Integer) payload.get("stock"));

        Long categoryId = ((Number) payload.get("id_categoria")).longValue();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con el ID '" + categoryId + "' no fue encontrada."));
        product.setCategory(category);

        final Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable(value = "id") Long productId,
                                                @RequestBody Map<String, Object> updates) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el ID '" + productId + "' no fue encontrado."));

        updates.forEach((key, value) -> {
            switch (key) {
                case "nombre":
                    product.setNombre((String) value);
                    break;
                case "descripcion":
                    product.setDescripcion((String) value);
                    break;
                case "precio":
                    product.setPrecio(((Number) value).doubleValue());
                    break;
                case "stock":
                    product.setStock((Integer) value);
                    break;
                case "id_categoria":
                    Long categoryId = ((Number) value).longValue();
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("La categoría con el ID '" + categoryId + "' no fue encontrada."));
                    product.setCategory(category);
                    break;
            }
        });

        final Product patchedProduct = productRepository.save(product);
        return ResponseEntity.ok(patchedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el ID '" + productId + "' no fue encontrado."));
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}