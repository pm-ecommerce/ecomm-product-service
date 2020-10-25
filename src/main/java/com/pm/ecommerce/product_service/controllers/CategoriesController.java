package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.product_service.models.CategoryResponse;
import com.pm.ecommerce.product_service.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoryService categoryservice;

    @PostMapping("/{vendorsid}")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody Category category, @PathVariable int vendorsid) {
        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        try {
            CategoryResponse created = categoryservice.createCategory(category, vendorsid);
            response.setStatus(200);
            response.setData(created);
            response.setMessage("successfully created category");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorid}")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllcategories(@PathVariable int vendorid) {
        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>();
        try {
            List<CategoryResponse> allcatagories = categoryservice.findAllCategories(vendorid);
            response.setData(allcatagories);
            response.setMessage("Get All categories by vendorid id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorid}/{categoryid}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getcatagory(@PathVariable int vendorid, @PathVariable int categoryid) {
        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        try {
            CategoryResponse category = categoryservice.findBycatagoriesbyID(vendorid, categoryid);
            response.setData(category);
            response.setMessage("Get categories by id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
