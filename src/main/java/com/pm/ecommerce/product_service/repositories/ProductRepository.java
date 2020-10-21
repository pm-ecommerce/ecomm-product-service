package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product getBySlug(String slug);
}
