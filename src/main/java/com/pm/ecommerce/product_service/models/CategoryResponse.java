package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.Category;
import lombok.Data;

@Data
public class CategoryResponse {
    int id;
    String name;
    String image;
    String parent;

    public CategoryResponse(Category category) {
        id = category.getId();
        name = category.getName();

        if (category.getImage() != null) {
            image = category.getImage().getName();
        }

        if (category.getParent() != null) {
            parent = category.getParent().getName();
        }
    }
}
