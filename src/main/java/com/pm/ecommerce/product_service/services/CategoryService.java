package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.product_service.repositories.CategoryRepository;
import com.pm.ecommerce.product_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryrepository;


    @Autowired
    VendorRepository vendorrepository;


    public Category createCategoy(Category category, int vendorid) throws Exception {

        Category existed = categoryrepository.findByName(category.getName());

        Vendor vendorexist = vendorrepository.findById(vendorid).get();


        String vendorname = vendorexist.getBusinessName();


        if (vendorexist == null) {

            throw new Exception("vendor not exist");
        }

        if (existed != null && vendorexist != null) {

            throw new Exception("category is allready Existed");

        }
        if (category.getName() == null) {

            throw new Exception("category should not be null");
        }
        category.setName(category.getName());


        return categoryrepository.save(category);

    }


     public List<Category> findAllCatagories(int vendorid) throws Exception{

         Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
         if(exisistedvendor==null){

             throw new Exception("vendor not exist");
         }
            return categoryrepository.findAll();

     }


    public Category findBycatagoriesbyID(int vendorid,int catid) throws Exception {

        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
          Category existedvendor= categoryrepository.findById(catid).get();

            if(exisistedvendor==null&&existedvendor==null){

                throw new Exception("your file not found");
            }


        return existedvendor;
    }
    }


