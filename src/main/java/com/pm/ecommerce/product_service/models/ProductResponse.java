package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.enums.ProductStatus;
import lombok.Data;

@Data
public class ProductResponse {
    int id;
    String name;
    String slug;
    String vendor;
    double price;
    ProductStatus status;
    CategoryResponse category;

    public ProductResponse(Product product) {
        id = product.getId();
        name = product.getName();
        slug = product.getSlug();
        price = product.getPrice();

        status = product.getStatus();
        category = new CategoryResponse(product.getCategory());
        vendor = product.getVendor().getBusinessName();
    }
}
