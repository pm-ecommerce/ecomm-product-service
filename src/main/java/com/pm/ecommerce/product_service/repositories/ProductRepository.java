package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findOneBySlug(String slug);

    Product findByIdAndVendorId(int producid, int vendorid);

    Product findByName(int productid);

    List<Product> findAllByVendorId(int vendorId);

    Page<Product> findAllByVendorId(int vendorId, Pageable pageable);

    Product findByStatus(ProductStatus status);

    List<Product> findAllByStatus(ProductStatus status);

    Page<Product> findAllByStatus(ProductStatus status,Pageable pageable);
    Product findByIdAndStatus(int productid,ProductStatus status);





}
