package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {


             Category findByName(String name);
             //Category findById(int catid);


}
