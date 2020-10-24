package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.product_service.repositories.CategoryRepository;
import com.pm.ecommerce.product_service.repositories.ProductRepository;
import com.pm.ecommerce.product_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


        Vendor vendorexist = vendorrepository.findById(vendorid).get();
        Category categoryexisted = categoryrepository.findById(vendorexist.getId()).get();

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
        product.setCategory(categoryexisted);
        product.setVendor(vendorexist);


        product.setStatus(ProductStatus.CREATED);

        return productrepository.save(product);
    }


    public Product updateproduct(int vendorid,int productid,Product product) throws Exception {

        // problem is name always should be given

        Random rand = new Random();

        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
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





    public List<Product> findAllProducts(int vendorid) throws Exception{

        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
        if(exisistedvendor==null){

            throw new Exception("vendor not exist");
        }
        return productrepository.findAll();

    }
    public Product findByproductsByID(int vendorid,int productid) throws Exception {

        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
        Product existedproduct= productrepository.findById(productid).get();

        if(exisistedvendor==null&&existedproduct==null){

            throw new Exception("your file not found");
        }


        return existedproduct;
    }

    public void deleteByproductsByID(int vendorid,int productid) throws Exception {

        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
        Product existedproduct= productrepository.findById(productid).get();

        if(exisistedvendor==null&&existedproduct==null){

            throw new Exception("your file not found");
        }
             String name=existedproduct.getName();

         existedproduct.setName(name);
         productrepository.delete(existedproduct);
    }







}