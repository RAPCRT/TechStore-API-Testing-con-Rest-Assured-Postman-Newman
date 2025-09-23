package com.techstore.api;

import com.techstore.api.model.Category;
import com.techstore.api.model.Product;
import com.techstore.api.repository.CategoryRepository;
import com.techstore.api.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBaseApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            Category cat1 = categoryRepository.save(new Category("Computadoras", "Incluye laptops, desktops y accesorios."));
            Category cat2 = categoryRepository.save(new Category("Periféricos", "Teclados, mouses y otros accesorios."));
            //Category cat3 = categoryRepository.save(new Category("Monitores", "Pantallas de alta resolución."));

            Product p1 = new Product();
            p1.setNombre("Laptop Pro X15");
            p1.setDescripcion("Laptop de alto rendimiento con 16GB de RAM y 1TB SSD.");
            p1.setPrecio(1499.99);
            p1.setStock(50);
            p1.setCategory(cat1);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setNombre("Teclado Mecánico RGB");
            p2.setDescripcion("Teclado para gaming con switches rojos.");
            p2.setPrecio(89.90);
            p2.setStock(120);
            p2.setCategory(cat2);
            productRepository.save(p2);

        };
    }
}