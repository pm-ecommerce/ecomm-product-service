package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Category;
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

    public CategoryResponse createCategory(Category category) throws Exception {
        if (category == null) {
            throw new Exception("Data expected with this request.");
        }

        System.out.println("*****************************1");
        if (category.getName() == null) {
            throw new Exception("category should not be null");
        }

        System.out.println("*****************************2");
        Category existingCategory = categoryrepository.findByName(category.getName());

        if (existingCategory != null && !existingCategory.isDeleted()) {
            throw new Exception("category already exists");
        }
        System.out.println("*****************************3");

        // undelete an existing category which has the same name
        if (existingCategory != null) {
            existingCategory.setDeleted(false);
            if (category.getImage() != null) {
                existingCategory.setImage(category.getImage());
            }

            categoryrepository.save(existingCategory);
        }

          if(category.getParent()==null){

              System.out.println("*****************************no parent");
          }
          else{

              category.setParent(category.getParent());
          }
           //category.setParent(category.getParent());
        // add validation for parent category
        // do the same for update

        //this means the category has a parent category


        return new CategoryResponse(categoryrepository.save(category));
    }

    public List<CategoryResponse> findAllCategories() throws Exception {
        return categoryrepository.findAllByIsDeleted(false)
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    public CategoryResponse findCategoryByID(int catid) throws Exception {
        Category existingCategory = categoryrepository.findById(catid).orElse(null);

        if (existingCategory == null || existingCategory.isDeleted()) {
            throw new Exception("Category not found");
        }

        return new CategoryResponse(existingCategory);
    }

    // get parent categories
    public List<CategoryResponse> findAllParentCategories(int cataid) throws Exception {

        return categoryrepository.findAllByParentIdAndIsDeleted(cataid, false)
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }


}


