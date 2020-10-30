package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.product_service.models.CategoryResponse;
import com.pm.ecommerce.product_service.models.VendorResponse;
import com.pm.ecommerce.product_service.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {


    @Autowired
    VendorService vendorservice;

    @PostMapping("/update/{vendorid}")
    public ResponseEntity<ApiResponse<VendorResponse>> updatevendorpassword(@RequestBody Vendor vendor, @PathVariable int vendorid){

        ApiResponse<VendorResponse> response = new ApiResponse<>();

        try {
            VendorResponse updated = vendorservice.updatevendorinformation(vendor,vendorid);
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
