package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class VendorResponse {

    int id;
    String bussinessname;
    VendorStatus status;
    String email;

    public VendorResponse(Vendor vendor){
          id=vendor.getId();
          bussinessname=vendor.getBusinessName();
          status=vendor.getStatus();
          email=vendor.getEmail();


    }
}
