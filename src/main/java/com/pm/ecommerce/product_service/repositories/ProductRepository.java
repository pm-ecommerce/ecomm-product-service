package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findOneBySlug(String slug);

    Product findByIdAndVendorId(int producid,int vendorid);

    Product findByName(int productid);
}
