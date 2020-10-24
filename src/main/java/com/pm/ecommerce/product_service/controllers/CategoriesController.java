package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.product_service.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/catagories")
public class CategoriesController {

    @Autowired
    CategoryService categoryservice;

    @PostMapping("/{vendorsid}")
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody Category category, @PathVariable int vendorsid) {

        ApiResponse<Category> response = new ApiResponse<Category>();

        if (category == null) {

            response.setStatus(400);

            response.setMessage("Categories not Created");
        }
        try {
            Category created = categoryservice.createCategoy(category,vendorsid);

            if (created != null) {

                response.setStatus(200);
                response.setData(created);
                response.setMessage("successfully created category");
            }
        } catch (Exception e) {

            response.setMessage(e.getMessage());
            response.setStatus(500);
        }


        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorid}")
    public ResponseEntity<ApiResponse<Category>>getAllcategories(@PathVariable int vendorid) {

        ApiResponse<Category> response = new ApiResponse<>();



        try {
            List<Category>allcatagories = categoryservice.findAllCatagories(vendorid);

            response.setData((Category) allcatagories);
            response.setMessage("Get All categories by vendorid id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{vendorid}/{categoryid}")
    public ResponseEntity<ApiResponse<Category>> getcatagory(@PathVariable int vendorid,

                                                              @PathVariable int categoryid) {
        ApiResponse<Category> response = new ApiResponse<>();

        try {
            Category category = categoryservice.findBycatagoriesbyID(vendorid,categoryid);
            //category.setId(0);
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