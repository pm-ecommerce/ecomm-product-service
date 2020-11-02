package com.pm.ecommerce.product_service.notifications.events;

import com.pm.ecommerce.entities.Product;
import org.springframework.context.ApplicationEvent;

public class ProductDisapprovedEvent extends ApplicationEvent {
    private final Product product;
    public ProductDisapprovedEvent(Object source, Product product) {
        super(source);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
