package com.pm.ecommerce.product_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.product_service.models.EmployeeResponse;
import com.pm.ecommerce.product_service.models.VendorResponse;
import com.pm.ecommerce.product_service.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {


        @Autowired
      EmployeeService employeeservice;

    @PostMapping("/update/{employeeid}")
    public ResponseEntity<ApiResponse<EmployeeResponse>>  updateemployeerpassword(@RequestBody Employee employee, @PathVariable int employeeid){

        ApiResponse<EmployeeResponse> response = new ApiResponse<>();

        try {
            EmployeeResponse updated = employeeservice.updateEmployeeInformation(employee,employeeid);
            response.setStatus(200);
            response.setData(updated);
            response.setMessage("successfully update employee password");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
        }
        return ResponseEntity.ok(response);

}



}
