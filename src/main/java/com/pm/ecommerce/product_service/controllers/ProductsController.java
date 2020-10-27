package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.product_service.models.ProductResponse;
import com.pm.ecommerce.product_service.models.SingleProductResponse;
import com.pm.ecommerce.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/products/")
public class ProductsController {

    @Autowired
    ProductService productservice;

    @PostMapping("/{vendorId}")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody() Product product, @PathVariable int vendorId) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse created = productservice.creatProduct(product, vendorId);
            response.setStatus(200);
            response.setData(created);
            response.setMessage("successfully created category");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{vendorId}/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@RequestBody Product product,
                                                                      @PathVariable int vendorId,
                                                                      @PathVariable int productId) throws Exception {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse updated = productservice.updateproduct(product, vendorId, productId);
            response.setMessage("update product successfully");
            response.setStatus(200);

            response.setData(updated);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllproductsByVendor(@PathVariable int vendorId) {
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>();
        try {
            List<ProductResponse> allProducts = productservice.findAllProducts(vendorId);
            response.setData(allProducts);
            response.setMessage("Get All products by vendor");
        } catch (Exception e) {
            response.setStatus(500);

            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorId}/status/{statusid}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllproductsBystatus(@PathVariable ProductStatus statusid) {
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>();
        try {
            List<ProductResponse> allProducts = productservice.findAllProductsByStatus(statusid);

            response.setData(allProducts);
            response.setMessage("Get All products by status id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorId}/{productId}")
    public ResponseEntity<ApiResponse<SingleProductResponse>> getproductId(@PathVariable int vendorId, @PathVariable int productId) {
        ApiResponse<SingleProductResponse> response = new ApiResponse<>();

        try {
            SingleProductResponse product = productservice.findByproductsByID(vendorId, productId);
            response.setData(product);
            response.setMessage("your product with Id " + productId);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{vendorId}/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteproductsid(@PathVariable int vendorId, @PathVariable int productId) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();

        try {
            ProductResponse deletedproduct = productservice.deleteByproductsByID(vendorId, productId);
            response.setData(deletedproduct);
            response.setMessage("your product is removed ");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{vendorId}/{productId}/send-for-approval")
    public ResponseEntity<ApiResponse<ProductResponse>> sendForApproval(@PathVariable int vendorId, @PathVariable int productId) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse sentproduct = productservice.sendForApproval(vendorId, productId);
            response.setData(sentproduct);
            response.setMessage("Your product has been sent for approval.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/approvad-product")
    public ResponseEntity<ApiResponse<ProductResponse>> approvedProduct(@PathVariable int productId) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse sentproduct = productservice.approveProduct(productId);
            response.setData(sentproduct);
            response.setMessage("Congratulations Your product has Approved.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/rejected-product")
    public ResponseEntity<ApiResponse<ProductResponse>> RejectedProduct(@PathVariable int productId) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse rejectedproduct = productservice.rejectProduct(productId);
            response.setData(rejectedproduct);
            response.setMessage("Congratulations Your product has Approved.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}








