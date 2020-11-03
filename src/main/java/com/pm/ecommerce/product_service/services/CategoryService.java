package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import com.pm.ecommerce.product_service.models.CategoryResponse;
import com.pm.ecommerce.product_service.models.PagedResponse;
import com.pm.ecommerce.product_service.models.ProductResponse;
import com.pm.ecommerce.product_service.repositories.CategoryRepository;
import com.pm.ecommerce.product_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        if (category.getName() == null) {
            throw new Exception("category should not be null");
        }

        Category existingCategory = categoryrepository.findByName(category.getName());

        if (existingCategory != null && !existingCategory.isDeleted()) {
            throw new Exception("category already exists");
        }


        // undelete an existing category which has the same name
        if (existingCategory != null) {
            existingCategory.setDeleted(false);
            if (category.getImage() != null) {
                existingCategory.setImage(category.getImage());
            }

            categoryrepository.save(existingCategory);
        }

        //validating sub categories

         if(category.getParent()!=null) {

             Category existing = categoryrepository.findByParent(category.getParent().getId());

             if (existing == null) {

                 throw new Exception("you are trying to create unknow parent categories");
             } else {

                 category.setParent(existing);
             }

         }
         return new CategoryResponse(categoryrepository.save(category));
    }


    public CategoryResponse updateCategory(Category category,int catid) throws Exception {

         Category existed=categoryrepository.findById(catid).orElse(null);


        //Vendor existedvendor=vendorrepository.findById(existed.)
         if(existed==null){

             throw new Exception("categories not found");
         }

        if (category == null) {
            throw new Exception("Data expected with this request.");
        }


        if (category.getName() == null) {
            throw new Exception("category should not be null");
        }


        Category existingCategory = categoryrepository.findByName(category.getName());




        // undelete an existing category which has the same name
        if (existingCategory != null) {
            existingCategory.setDeleted(false);
            if (category.getImage() != null) {
                existingCategory.setImage(category.getImage());
            }

            categoryrepository.save(existingCategory);
        }

        //validating sub categories

        if(category.getParent()!=null){

            Category existing=categoryrepository.findByParent(category.getParent().getId());

            if(existing==null){

                throw new Exception("you are trying to create unknow parent categories");
            }
            else{

                category.setParent(existing);
            }

        }

          existed.setName(category.getName());



        return new CategoryResponse(categoryrepository.save(existed));
    }

    public PagedResponse<CategoryResponse> findAllCategories(int page, int itemsPerPage) throws Exception {
        if (page < 1) {
            throw new Exception("Page number is invalid.");
        }

        Pageable paging = PageRequest.of(page - 1, itemsPerPage);
        Page<Category> pagedResult = categoryrepository.findAllByIsDeleted(false, paging);

        int totalPages = pagedResult.getTotalPages();

        List<CategoryResponse> categories = pagedResult.toList().stream().map(CategoryResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, page, itemsPerPage, categories);
    }

    public CategoryResponse findCategoryByID(int catid) throws Exception {
        Category existingCategory = categoryrepository.findById(catid).orElse(null);

        if (existingCategory == null || existingCategory.isDeleted()) {
            throw new Exception("Category not found");
        }

        return new CategoryResponse(existingCategory);
    }

    // get parent categories
    public PagedResponse<CategoryResponse> findAllParentCategories(int cataid,int page, int itemsPerPage) throws Exception {

        if (page < 1) {
            throw new Exception("Page number is invalid.");
        }

        Pageable paging = PageRequest.of(page - 1, itemsPerPage);
        Page<Category> pagedResult = categoryrepository.findAllByParentIdAndIsDeleted(cataid,false, paging);

        int totalPages = pagedResult.getTotalPages();

        List<CategoryResponse> categories = pagedResult.toList().stream().map(CategoryResponse::new).collect(Collectors.toList());


        return new PagedResponse<>(totalPages, page, itemsPerPage, categories);
    }


    // get child categories
    public PagedResponse<CategoryResponse>  findAllSubCategories(int cartId,int page, int itemsPerPage) throws Exception {
        if (page < 1) {
            throw new Exception("Page number is invalid.");
        }

        Pageable paging = PageRequest.of(page - 1, itemsPerPage);
        Page<Category> pagedResult = categoryrepository.findAllByParentIdAndIsDeleted(cartId,false, paging);

        int totalPages = pagedResult.getTotalPages();

        List<CategoryResponse> categories = pagedResult.toList().stream().map(CategoryResponse::new).collect(Collectors.toList());


        return new PagedResponse<>(totalPages, page, itemsPerPage, categories);
    }

    public CategoryResponse deleteBycategorybyid(int categoid) throws Exception {
        Category existed=categoryrepository.findById(categoid).orElse(null);
        if (existed == null) {

            throw new Exception("your catagories not existed");
        }



        categoryrepository.delete(existed);
        return new CategoryResponse(existed);
    }



}


