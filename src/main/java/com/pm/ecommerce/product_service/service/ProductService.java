package com.pm.ecommerce.product_service.service;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.product_service.repository.CategoryRepository;
import com.pm.ecommerce.product_service.repository.ProductRepository;
import com.pm.ecommerce.product_service.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ProductService {

    @Autowired
    ProductRepository productrepository;
    @Autowired
    CategoryRepository categoryrepository;

    @Autowired
    VendorRepository vendorrepository;

    protected String generateslug(String str) {

        return str.toLowerCase().replaceAll("[^a-z0-9-]", "");
    }

    public Product creatproduct(Product product, int vendorid) throws Exception {

        Product existing = productrepository.getBySlug(this.generateslug(product.getName()));


        Vendor vendorexist = vendorrepository.getById(vendorid);
        Category categoryexisted = categoryrepository.findById(vendorexist.getId());

        String vendorname = vendorexist.getBusinessName();
        String categoryname = categoryexisted.getName();

        if (product.getDescription() == null || product.getName() == null || product.getPrice() <= 0) {

            throw new Exception("you have to fill all detail of Product");
        }

        if (existing != null && vendorexist != null && categoryexisted != null) {

            throw new Exception("product is already avvialvble");
        }

        product.setSlug(this.generateslug(product.getName()));
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        vendorexist.setBusinessName(vendorname);
        categoryexisted.setName(categoryname);


        product.setStatus(ProductStatus.CREATED);

        return productrepository.save(product);
    }


    public Product updateproduct(Product product, int vendorid, int productid) throws Exception {

        // problem is name always should be given

        Random rand = new Random();

        Vendor exisistedvendor = vendorrepository.getById(vendorid);
        // Vendor businessname=vendorrepository.getByBusinessName(exisistedvendor.getId());


        Product existedproduct = productrepository.findById(productid).get();

        Product existingname = productrepository.getByName(existedproduct.getName());

        String update = "0123";

        //  Product existing=productrepository.getBySlug(this.generateslug(existingname.getName()+update));

        if (product.getDescription() == null) {

            product.setDescription(existedproduct.getDescription());
        } else {

            product.setDescription(product.getDescription());
        }
        if (product.getPrice() <= 0) {

            product.setPrice(existedproduct.getPrice());

        } else {

            product.setPrice(product.getPrice());
        }

        if (product.getName() == null && product.getDescription() == null && product.getPrice() == 0) {

            throw new Exception("you didn't update nothing");

        }

        product.setSlug(this.generateslug(product.getName()));

        //exisistedvendor.setBusinessName(exisistedvendor.getBusinessName());

        product.setStatus(ProductStatus.UPDATED);
        return productrepository.save(product);
    }



    public Product findById(int vendorid, int productid) throws Exception {
        Vendor exisistedvendor = vendorrepository.getById(vendorid);
          if(exisistedvendor==null){

              throw new Exception("vendor not found");
          }
        Optional<Product> result = productrepository.findById(productid);

        Product product = null;
        if (result.isPresent()) {
            product = result.get();
        } else {
            throw new RuntimeException("Did not find your product id - " + productid);
        }
        return product;
    }







}