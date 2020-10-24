package com.pm.ecommerce.product_service.service;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.product_service.repository.CategoryRepository;
import com.pm.ecommerce.product_service.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryrepository;


    @Autowired
    VendorRepository vendorrepository;


    public Category createCategoy(Category category,int vendorid) throws Exception {

        Category existed = categoryrepository.findByName(category.getName());

        Vendor vendorexist = vendorrepository.getById(vendorid);


        String vendorname = vendorexist.getBusinessName();





          if(vendorexist==null){

              throw  new Exception("vendor not exist");
          }

        if (existed != null&&vendorexist!=null) {

            throw new Exception("category is allready Existed");

        }
        if (category.getName() == null) {

            throw new Exception("category should not be null");
        }
                   category.setName(category.getName());

                      vendorexist.setBusinessName(vendorname);


        return categoryrepository.save(category);

    }

    public List<Category> getAllCategories() {
        return categoryrepository.findAll();
    }


    public Category findById(int catid) throws Exception{
       Category result = categoryrepository.findById(catid);
  if(result==null){

      throw new Exception("not found catgories");
  }

        return result;
    }

}
