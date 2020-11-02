package com.pm.ecommerce.product_service.notifications.event_data;

import com.pm.ecommerce.entities.Product;
import lombok.Data;

@Data
public class EmailProduct {
    int id;
    String name;
    String userName;
    String category;
    String image;

    public EmailProduct(Product product) {
        id = product.getId();
        name = product.getName();
        userName = product.getVendor().getBusinessName();
        category = product.getCategory().getName();
    }
}
