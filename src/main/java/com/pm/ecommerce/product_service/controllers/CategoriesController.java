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
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoryService categoryservice;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody Category category) {
        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        try {
            CategoryResponse created = categoryservice.createCategory(category);
            response.setStatus(200);
            response.setData(created);
            response.setMessage("successfully created category");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllcategories() {
        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>();
        try {
            List<CategoryResponse> allcategories = categoryservice.findAllCategories();
            response.setData(allcategories);
            response.setMessage("Get All categories");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(@PathVariable int categoryId) {
        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        try {
            CategoryResponse category = categoryservice.findCategoryByID(categoryId);
            response.setData(category);
            response.setMessage("Get category by id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
