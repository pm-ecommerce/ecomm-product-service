package com.pm.ecommerce.product_service.repository;

import com.pm.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Integer> {


             Category findByName(String name);
             Category findById(int catid);


}
