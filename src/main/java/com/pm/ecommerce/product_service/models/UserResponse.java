package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class UserResponse {
    int id;
    String name;

    String email;

    public UserResponse(User user) {
        id = user.getId();
        name = user.getName();

        email = user.getEmail();


    }
}