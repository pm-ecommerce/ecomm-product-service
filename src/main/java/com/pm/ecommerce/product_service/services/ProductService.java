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

    public Product createProduct(Product product, int vendorId) throws Exception {
        //assume a vendor with id 1 is trying to create a product
        //get all products of vendor with id 1

        // check if the product already contain a product with the slug
        // of this new product
        // if it does throw an except that the product already exists

        // add validations here
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
