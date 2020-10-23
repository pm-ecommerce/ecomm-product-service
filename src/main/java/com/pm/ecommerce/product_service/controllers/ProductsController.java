package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    ProductService service;

    @PostMapping("{vendorId}")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody() Product product, @PathVariable int vendorId) {
        ApiResponse<Product> response = new ApiResponse<>();
        try {
            Product product1 = service.createProduct(product, vendorId);
            response.setMessage("Product created successfully.");
            response.setData(product1);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
