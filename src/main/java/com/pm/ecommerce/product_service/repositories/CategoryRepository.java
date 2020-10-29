package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);

    List<Category> findAllByIsDeleted(boolean isDeleted);
    Page<Category> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    List<Category> findAllByParentIdAndIsDeleted(Integer catId, boolean b);

    Category findByParentAndIsDeleted(int parentid, boolean b);



}
