package com.example.product_inventory.controller;

import com.example.product_inventory.model.Product;
import com.example.product_inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        log.debug("Received request to create product: {}", product);
        Product created = service.create(product);
        log.info("Product created with ID: {} and SKU: {}", created.getId(), created.getSku());
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        log.debug("Received request to list all products");
        List<Product> products = service.findAll();
        log.info("Returning {} products", products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        log.debug("Received request to get product by ID: {}", id);
        Product product = service.findById(id);
        log.info("Returning product with ID: {}", id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product update) {
        log.debug("Received request to update product ID {} with payload: {}", id, update);
        Product updated = service.update(id, update);
        log.info("Product updated with ID: {}", id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.debug("Received request to delete product ID {}", id);
        service.delete(id);
        log.info("Deleted product with ID: {}", id);
    }
}
