package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Attribute;
import com.pm.ecommerce.entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {

}
