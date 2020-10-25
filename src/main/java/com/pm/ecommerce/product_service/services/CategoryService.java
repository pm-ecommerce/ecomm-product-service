package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.product_service.models.CategoryResponse;
import com.pm.ecommerce.product_service.repositories.CategoryRepository;
import com.pm.ecommerce.product_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryrepository;


    @Autowired
    VendorRepository vendorrepository;


    public CategoryResponse createCategory(Category category, int vendorid) throws Exception {
        if (category == null) {
            throw new Exception("Data expected with this request.");
        }

        if (category.getName() == null) {
            throw new Exception("category should not be null");
        }

        Category existingCategory = categoryrepository.findByNameAndVendorId(category.getName(), vendorid);

        if (existingCategory != null && existingCategory.getVendor().getId() == vendorid && !existingCategory.isDeleted()) {
            throw new Exception("category already exists");
        }

        Vendor vendor = vendorrepository.findById(vendorid).orElse(null);
        if (vendor == null) {
            throw new Exception("vendor not exist");
        }

        category.setVendor(vendor);

        // undelete an existing category which has the same name
        if (existingCategory != null && existingCategory.getVendor().getId() == vendorid) {
            existingCategory.setDeleted(false);
            return new CategoryResponse(categoryrepository.save(existingCategory));
        }

        return new CategoryResponse(categoryrepository.save(category));
    }

    public List<CategoryResponse> findAllCategories(int vendorid) throws Exception {
        Vendor vendor = vendorrepository.findById(vendorid).orElse(null);

        if (vendor == null) {
            throw new Exception("vendor does not exist");
        }

        return categoryrepository.findAllByVendorIdAndIsDeleted(vendorid, false)
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }


    public CategoryResponse findBycatagoriesbyID(int vendorid, int catid) throws Exception {
        Category existingCategory = categoryrepository.findByIdAndVendorId(catid, vendorid);

        if (existingCategory == null || existingCategory.isDeleted()) {
            throw new Exception("Category not found");
        }

        return new CategoryResponse(existingCategory);
    }
}


