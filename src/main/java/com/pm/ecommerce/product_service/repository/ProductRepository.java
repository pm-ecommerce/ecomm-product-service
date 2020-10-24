package com.pm.ecommerce.product_service.repository;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product ,Integer> {

         Product getBySlug(String slug);

        Product getByName(String name);

         Product getById(int id);

         void deleteById(int productid);













}
