package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    ProductService service;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody() Product product) {
        ApiResponse<Product> response = new ApiResponse<>();
        try {
            Product product1 = service.createProduct(product);
            response.setMessage("Product created successfully.");
            response.setData(product1);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }

        return ResponseEntity.ok(response);
    }
}
