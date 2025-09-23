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

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = categoryRepository.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con el ID '" + categoryId + "' no fue encontrada."));
        return ResponseEntity.ok().body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId,
                                                   @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con el ID '" + categoryId + "' no fue encontrada."));
        category.setNombre(categoryDetails.getNombre());
        category.setDescripcion(categoryDetails.getDescripcion());
        final Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con el ID '" + categoryId + "' no fue encontrada."));
        // Optional: Add logic to handle products associated with this category
        productRepository.deleteAll(productRepository.findByCategoryId(categoryId));
        categoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    public List<Product> getProductsByCategory(@PathVariable(value = "id") Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("La categoría con el ID '" + categoryId + "' no fue encontrada.");
        }
        return productRepository.findByCategoryId(categoryId);
    }
}