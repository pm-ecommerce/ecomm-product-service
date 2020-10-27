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

    @PostMapping("/{vendorid}")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody() Product product, @PathVariable int vendorid) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse created = productservice.creatProduct(product, vendorid);
            response.setStatus(200);
            response.setData(created);
            response.setMessage("successfully created category");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{vendourid}/{productid}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@RequestBody Product product,
                                                                      @PathVariable int vendourid,
                                                                      @PathVariable int productid) throws Exception {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse updated = productservice.updateproduct(product, vendourid, productid);
            response.setMessage("update product successfully");
            response.setStatus(200);

            response.setData(updated);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorid}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllproductsByVendor(@PathVariable int vendorid) {
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>();
        try {
            List<ProductResponse> allProducts = productservice.findAllProducts(vendorid);
            response.setData(allProducts);
            response.setMessage("Get All products by vendorid id");
        } catch (Exception e) {
            response.setStatus(500);

            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorid}/status/{statusid}")
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

    @GetMapping("/{vendorid}/{productid}")
    public ResponseEntity<ApiResponse<SingleProductResponse>> getproductid(@PathVariable int vendorid, @PathVariable int productid) {
        ApiResponse<SingleProductResponse> response = new ApiResponse<>();

        try {
            SingleProductResponse product = productservice.findByproductsByID(vendorid, productid);
            response.setData(product);
            response.setMessage("your product with Id " + productid);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{vendorid}/{productid}")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteproductsid(@PathVariable int vendorid, @PathVariable int productid) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();

        try {
            ProductResponse deletedproduct = productservice.deleteByproductsByID(vendorid, productid);
            response.setData(deletedproduct);
            response.setMessage("your product is removed ");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{vendorid}/{productid}/send-for-approval")
    public ResponseEntity<ApiResponse<ProductResponse>> sendForApproval(@PathVariable int vendorid, @PathVariable int productid) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse sentproduct = productservice.sendForApproval(vendorid, productid);
            response.setData(sentproduct);
            response.setMessage("Your product has been sent for approval.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productid}/approvad-product")
    public ResponseEntity<ApiResponse<ProductResponse>> approvedProduct(@PathVariable int productid) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse sentproduct = productservice.approveProduct(productid);
            response.setData(sentproduct);
            response.setMessage("Congratulations Your product has Approved.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productid}/rejected-product")
    public ResponseEntity<ApiResponse<ProductResponse>> RejectedProduct(@PathVariable int productid) {
        ApiResponse<ProductResponse> response = new ApiResponse<>();
        try {
            ProductResponse rejectedproduct = productservice.rejectProduct(productid);
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








