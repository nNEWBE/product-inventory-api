package com.example.product_inventory.controller;

import com.example.product_inventory.exception.Response;
import com.example.product_inventory.model.Product;
import com.example.product_inventory.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Response<Product>> create(@Valid @RequestBody Product product, HttpServletRequest request) {
        log.debug("Received request to create product: {}", product);
        Product created = service.create(product);
        log.info("Product created with ID: {} and SKU: {}", created.getId(), created.getSku());
        Response<Product> body = Response.<Product>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .path(request.getRequestURI())
                .data(created)
                .build();
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(body);
    }

    @GetMapping
    public ResponseEntity<Response<List<Product>>> findAll(HttpServletRequest request) {
        log.debug("Received request to list all products");
        List<Product> products = service.findAll();
        log.info("Returning {} products", products.size());
        Response<List<Product>> body = Response.<List<Product>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .path(request.getRequestURI())
                .data(products)
                .build();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> findById(@PathVariable Long id, HttpServletRequest request) {
        log.debug("Received request to get product by ID: {}", id);
        Product product = service.findById(id);
        log.info("Returning product with ID: {}", id);
        Response<Product> body = Response.<Product>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Product retrieved successfully")
                .path(request.getRequestURI())
                .data(product)
                .build();
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> update(@PathVariable Long id, @RequestBody Product update, HttpServletRequest request) {
        log.debug("Received request to update product ID {} with payload: {}", id, update);
        Product updated = service.update(id, update);
        log.info("Product updated with ID: {}", id);
        Response<Product> body = Response.<Product>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Product updated successfully")
                .path(request.getRequestURI())
                .data(updated)
                .build();
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id, HttpServletRequest request) {
        log.debug("Received request to delete product ID {}", id);
        service.delete(id);
        log.info("Deleted product with ID: {}", id);
        Response<Void> body = Response.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Product deleted successfully")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.ok(body);
    }
}
