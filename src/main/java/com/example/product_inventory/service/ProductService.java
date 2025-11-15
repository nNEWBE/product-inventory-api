package com.example.product_inventory.service;

import com.example.product_inventory.exception.InvalidSkuFormatException;
import com.example.product_inventory.exception.ProductNotFoundException;
import com.example.product_inventory.exception.SkuAlreadyExistsException;
import com.example.product_inventory.model.Product;
import com.example.product_inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;

    private static final Pattern SKU_PATTERN = Pattern.compile("^SKU-[A-Za-z0-9]{8}$");

    @Transactional
    public Product create(Product product) {
        log.debug("Attempting to create product: {}", product);
        validateSkuFormat(product.getSku());
        if (repository.existsBySku(product.getSku())) {
            log.warn("SKU already exists: {}", product.getSku());
            throw new SkuAlreadyExistsException(product.getSku());
        }
        Product saved = repository.save(product);
        log.info("Product created with ID: {} and SKU: {}", saved.getId(), saved.getSku());
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        log.debug("Fetching all products");
        List<Product> products = repository.findAll();
        log.info("Fetched {} products", products.size());
        return products;
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        log.debug("Fetching product with ID: {}", id);
        return repository.findById(id)
                .map(p -> {
                    log.info("Found product with ID: {}", id);
                    return p;
                })
                .orElseThrow(() -> {
                    log.warn("Failed to find product with ID: {}", id);
                    return new ProductNotFoundException(id);
                });
    }

    @Transactional
    public Product update(Long id, Product updated) {
        log.debug("Attempting to update product ID {} with data: {}", id, updated);
        Product existing = repository.findById(id).orElseThrow(() -> {
            log.warn("Failed to find product with ID: {} for update", id);
            return new ProductNotFoundException(id);
        });

        if (updated.getSku() != null && !updated.getSku().equals(existing.getSku())) {
            log.warn("Attempt to change immutable SKU from {} to {} for product ID {}", existing.getSku(), updated.getSku(), id);
            throw new InvalidSkuFormatException("SKU change is not allowed from " + existing.getSku() + " to " + updated.getSku());
        }

        validateSkuFormat(existing.getSku());

        if (updated.getName() != null) {
            String name = updated.getName();
            if (name.isBlank()) {
                throw new IllegalArgumentException("Product name must not be blank");
            }
            existing.setName(name);
        }
        if (updated.getDescription() != null) {
            String description = updated.getDescription();
            if (description.length() > 500) {
                throw new IllegalArgumentException("Description must not exceed 500 characters");
            }
            existing.setDescription(description);
        }
        if (updated.getPrice() != null) {
            Double price = updated.getPrice();
            if (price <= 0) {
                throw new IllegalArgumentException("Price must be a positive number");
            }
            existing.setPrice(price);
        }
        if (updated.getQuantity() != null) {
            Integer qty = updated.getQuantity();
            if (qty < 0) {
                throw new IllegalArgumentException("Quantity must be zero or more");
            }
            existing.setQuantity(qty);
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }

        Product saved = repository.save(existing);
        log.info("Product updated with ID: {}", saved.getId());
        return saved;
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Attempting to delete product with ID: {}", id);
        if (!repository.existsById(id)) {
            log.warn("Failed to find product with ID: {} for deletion", id);
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
        log.info("Product deleted with ID: {}", id);
    }

    private void validateSkuFormat(String sku) {
        if (sku == null || !SKU_PATTERN.matcher(sku).matches()) {
            throw new InvalidSkuFormatException(sku);
        }
    }
}
