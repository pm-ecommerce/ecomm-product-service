package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class EmployeeResponse {

    int id;
    String name;
    //VendorStatus status;
    String email;


    public EmployeeResponse(Employee employee){
        id=employee.getId();
        name=employee.getName();

        email=employee.getEmail();


    }
}
