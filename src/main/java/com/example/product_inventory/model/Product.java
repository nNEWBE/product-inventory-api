package com.example.product_inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "products",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_products_sku", columnNames = {"sku"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Product name must not be blank")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(length = 500)
    private String description;

    @NotBlank(message = "SKU must not be blank")
    @Column(nullable = false, updatable = false)
    private String sku;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 0, message = "Quantity must be zero or more")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "Status must not be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
