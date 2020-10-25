package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findOneBySlug(String slug);

    Product findByIdAndVendorId(int producid, int vendorid);

    Product findByName(int productid);

    List<Product> findAllByVendorId(int vendorId);

    Product findByStatus(ProductStatus status);



   List<Product> findAllByStatus(ProductStatus status);




}
