package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.product_service.models.EmployeeResponse;
import com.pm.ecommerce.product_service.models.ProductResponse;
import com.pm.ecommerce.product_service.models.UserResponse;
import com.pm.ecommerce.product_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userservice;

     @PostMapping("/update/{userid}")
     public ResponseEntity<ApiResponse<UserResponse>>updateuserpassword(@RequestBody User user, @PathVariable int userid){

         ApiResponse<UserResponse> response = new ApiResponse<>();

         try {
             UserResponse updated = userservice.updateuserinformation(user,userid);
             response.setStatus(200);
             response.setData(updated);
             response.setMessage("successfully update user password");
         } catch (Exception e) {
             response.setMessage(e.getMessage());
             response.setStatus(500);
         }
         return ResponseEntity.ok(response);


    }


}
