package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    Category findByName(String name);

    Category findByNameAndVendorId(String name, int vendorId);

    Category findByIdAndVendorId(int id, int vendorId);

    List<Category> findAllByVendorIdAndIsDeleted(int vendorId, boolean isDeleted);

    Category findByIdAndVendorIdAndIsDeleted(int category, int vendorId, boolean isDeleted);

    List<Category> findAllByIsDeleted(boolean isDeleted);

    List<Category> findAllByParentIdAndIsDeleted(Integer catId, boolean b);
}
