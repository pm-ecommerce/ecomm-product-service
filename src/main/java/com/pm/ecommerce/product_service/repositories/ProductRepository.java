package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.ScheduledDelivery;
import com.pm.ecommerce.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findOneBySlug(String slug);

    Product findByIdAndVendorId(int producid, int vendorid);

    Page<Product> findAllByVendorId(int vendorId, Pageable pageable);

    Page<Product> findAllByVendorIdAndStatus(int vendorId, ProductStatus stausid, Pageable paging);

    Page<Product> findAllByVendorIdAndStatusIn(int vendorId, List<ProductStatus> statusList, Pageable paging);

    Page<Product> findAllByStatusIn(List<ProductStatus> statusList, Pageable paging);

    Page<Product> findAllByStatus(ProductStatus stausid, Pageable paging);

}
