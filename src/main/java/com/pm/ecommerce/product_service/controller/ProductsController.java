package com.pm.ecommerce.product_service.controller;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Product;

import com.pm.ecommerce.entities.Vendor;

import com.pm.ecommerce.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/products/")
public class ProductsController {

          @Autowired
          ProductService productservice;

          @PostMapping("/{vendorid}")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody() Product product,
                                                              @PathVariable int vendorid){
              ApiResponse<Product> response=new ApiResponse<Product>();

        try{
               Product createdp=productservice.creatproduct(product,vendorid);
               response.setMessage("product is created successfully");
                 response.setData(createdp);

                  response.setStatus(200);

        }
       catch (Exception e){

         response.setMessage(e.getMessage());
         response.setStatus(500);
      }
        return ResponseEntity.ok(response);

        }




    @PutMapping("/update/{vendourid}/{productid}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable int vendourid,@PathVariable
            int productid, @RequestBody Product product) throws Exception {
        ApiResponse<Product> response = new ApiResponse<>();
        try {


            Product updated=productservice.updateproduct(product,productid,vendourid);
            response.setMessage("update product successfully");
            response.setStatus(200);

            response.setData(product);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }




    @GetMapping("/{vendorid}/{productid}")
    public ResponseEntity<ApiResponse<Product>> getproductid(@PathVariable int vendorid,@PathVariable int productid) {
        ApiResponse<Product> response = new ApiResponse<>();

        try {
            Product product = productservice.findById(vendorid,productid);
            response.setData(product);
            response.setMessage("your product is ");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}








