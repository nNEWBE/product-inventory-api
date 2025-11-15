package com.example.product_inventory.exception;

public class InvalidSkuFormatException extends RuntimeException {
    public InvalidSkuFormatException(String sku) {
        super("Invalid SKU format: " + sku + ". Expected format is 'SKU-XXXXXXXX' (8 alphanumeric characters)");
    }
}
