package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Category;

import com.pm.ecommerce.product_service.models.CategoryResponse;
import com.pm.ecommerce.product_service.models.PagedResponse;
import com.pm.ecommerce.product_service.repositories.NotificationRepository;
import com.pm.ecommerce.product_service.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoryService categoryservice;

    @Autowired
    KafkaTemplate<String, Long> template;

    @Autowired
    NotificationRepository repository;

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

    @PatchMapping("/{cateid}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@RequestBody Category category,@PathVariable int cateid) {
        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        try {
            CategoryResponse created = categoryservice.updateCategory(category,cateid);
            response.setStatus(200);
            response.setData(created);
            response.setMessage("successfully updated category");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>> getAllcategories(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage
    ) {
        ApiResponse<PagedResponse<CategoryResponse>> response = new ApiResponse<>();
        try {
//            Notification notification = new Notification();
//            notification.setSender("sa.giri@miu.edu");
//            notification.setReceiver("ioesandeep@gmail.com");
//            notification.setSubject("We have received your order");
//            notification.setMessage("Hey! \n Thank you for your order.");
//
//            repository.save(notification);
//
//            template.send("NotificationTopic", notification.getId());

            PagedResponse<CategoryResponse> allcategories = categoryservice.findAllCategories(page, itemsPerPage);
            response.setData(allcategories);
            response.setMessage("Get All categories");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/parent/{parentid}")
    public ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>>  getAllparentcategories(@PathVariable int parentid,
                                                                                                @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<CategoryResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<CategoryResponse> allcategories = categoryservice.findAllParentCategories(parentid,page, itemsPerPage);
            response.setData(allcategories);
            response.setMessage("Get All categories by parentid");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sub-catagories/{subid}")
    public ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>>  getAllsubcategories(@PathVariable int subid,
                                                                                                @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                                @RequestParam(name = "perPage", defaultValue = "20") int itemsPerPage) {
        ApiResponse<PagedResponse<CategoryResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<CategoryResponse> allcategories = categoryservice.findAllSubCategories(subid,page, itemsPerPage);
            response.setData(allcategories);
            response.setMessage("Get All categories by sub-category_id");
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

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> deleteCategory(@PathVariable int categoryId) {
        ApiResponse<CategoryResponse> response = new ApiResponse<>();
        try {
            CategoryResponse category = categoryservice.deleteBycategorybyid(categoryId);
            response.setData(category);
            response.setMessage("your category is deleted");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }


}
