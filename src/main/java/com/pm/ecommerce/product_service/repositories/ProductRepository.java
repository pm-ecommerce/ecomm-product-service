package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product ,Integer> {

         Product getBySlug(String slug);

        Product getByName(String name);

         Product getById(int id);















}
