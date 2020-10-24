package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.product_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository repository;

    protected String makeSlug(String str) {
        return str.toLowerCase().replaceAll("[\\s+]", "-");
    }

    public Product createProduct(Product product) throws Exception {
        // add validations here
        Vendor vendor = product.getVendor();
        // check if the vendor does not have the same product already
        // slug == prod slug and vendor == prod vendor
        Product existing = repository.getBySlug(this.makeSlug(product.getName()));

        // validate the category

        // validate the vendor

        if (existing != null) {
            throw new Exception("Product has already been registered");
        }

        product.setStatus(ProductStatus.CREATED);
        product.setSlug(this.makeSlug(product.getName()));

        return repository.save(product);
    }
}
