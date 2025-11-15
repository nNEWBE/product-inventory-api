# 🛍️ Product Inventory API

A simple **Spring Boot 3.5.7** REST API for managing products with validation, custom exceptions, logging, and persistence using **H2 in-memory database**.
Built using **Java 25** and **Gradle**.

---

## 📌 **Project Overview**

This project demonstrates how to build a fully functional Product Inventory API using Spring Boot.
It includes:

* CRUD operations
* Bean validation
* Custom exceptions
* Global exception handling
* Lombok-powered logging
* Spring Data JPA with H2
* Enum-based product status
* SKU validation + uniqueness constraint

---

## 🧱 **Technologies Used**

| Component       | Version                   |
| --------------- | ------------------------- |
| Java            | **25**                    |
| Spring Boot     | **3.5.7**                 |
| Gradle          | **8+**                    |
| H2 Database     | In-Memory                 |
| Lombok          | Latest                    |
| Spring Data JPA | Included with Spring Boot |

---

## 📂 **Project Structure**

```
src/main/java/com/example/inventory
 ├── controller
 ├── service
 ├── repository
 ├── exception
 ├── model
 │     ├── Product.java
 │     └── ProductStatus.java
 └── advice (GlobalExceptionHandler)
```

---

# 🧩 **Features**

### ✔ Product CRUD Operations

* Create product
* List all products
* Get product by ID
* Update product
* Delete product

### ✔ Validation Rules

* `name`: Not blank
* `description`: Optional (max 500 chars)
* `sku`: Required, must match pattern

    * Format → `SKU-XXXXXXXX` (8 alphanumeric characters)
* `price`: Positive
* `quantity`: Min 0
* `status`: Not null

### ✔ Custom Exceptions

* `ProductNotFoundException`
* `InvalidSkuFormatException`
* `SkuAlreadyExistsException`

### ✔ Logging with Lombok (@Slf4j)

* `INFO` → successful operations
* `DEBUG` → request data
* `WARN` → client-side errors
* `ERROR` → exception logs in global handler

---

## 🛢️ **H2 Database Console**

Enable H2 console by visiting:

```
http://localhost:8080/h2-console
```

---

# 🚀 **API Endpoints**

## **1️⃣ Create Product**

**POST** `/`

### Request Body Example

```json
{
  "name": "Wireless Bluetooth Headphones",
  "description": "Premium noise-cancelling over-ear headphones with 30-hour battery life",
  "sku": "SKU-A1B232D5",
  "price": 149.99,
  "quantity": 245,
  "status": "ACTIVE"
}
```

---

## **2️⃣ Get All Products**

**GET** `/`

---

## **3️⃣ Get Product by ID**

**GET** `/{id}`

---

## **4️⃣ Update Product**

**PUT** `/{id}`
(SKU cannot be changed.)

---

## **5️⃣ Delete Product**

**DELETE** `/{id}`

---

# ⚠️ **Error Handling Strategy**

Your API returns clean JSON error responses such as:

```json
{
  "error": "Product Not Found",
  "message": "No product found with ID: 15",
  "status": 404
}
```

Handled exceptions include:

| Exception                       | HTTP Status |
| ------------------------------- | ----------- |
| MethodArgumentNotValidException | 400         |
| ProductNotFoundException        | 404         |
| InvalidSkuFormatException       | 400         |
| SkuAlreadyExistsException       | 409         |

---

# 📝 **Sample Valid Product JSON**

```json
{
  "name": "Wireless Bluetooth Headphones",
  "description": "Premium noise-cancelling over-ear headphones with 30-hour battery life",
  "sku": "SKU-A1B232D5",
  "price": 149.99,
  "quantity": 245,
  "status": "ACTIVE"
}
```

---

# ▶️ **How to Run the Project**

### **Using Gradle**

```
./gradlew bootRun
```

### **Using IDE**

* Open project in IntelliJ / Eclipse
* Run `ProductInventoryApiApplication.java`

---

# 🎯 Conclusion

This project provides a complete example of building a clean, maintainable API with validation, exception handling, logging, and persistence. It is perfect for learning Spring Boot fundamentals with real-world best practices.

