package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.Image;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.ProductAttribute;
import com.pm.ecommerce.enums.ProductStatus;
import lombok.Data;

import java.util.Set;

@Data
public class SingleProductResponse {
    int id;
    String name;
    String slug;
    String description;
    double price;
    ProductStatus status;
    CategoryResponse category;
    Set<ProductAttribute> attributes;
    Set<Image> images;

    public SingleProductResponse(Product product) {
        id = product.getId();
        name = product.getName();
        price = product.getPrice();

        status = product.getStatus();
        description = product.getDescription();
        slug = product.getSlug();
        attributes = product.getAttributes();
        images = product.getImages();
        category = new CategoryResponse(product.getCategory());
    }
}
