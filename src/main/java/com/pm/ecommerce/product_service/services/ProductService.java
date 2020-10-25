package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.enums.VendorStatus;
import com.pm.ecommerce.product_service.repositories.CategoryRepository;
import com.pm.ecommerce.product_service.repositories.ProductRepository;
import com.pm.ecommerce.product_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productrepository;

    @Autowired
    CategoryRepository categoryrepository;

    @Autowired
    VendorRepository vendorrepository;

    protected String generateslug(String str, int vendorId) {
        return vendorId + "-" + str.toLowerCase().replaceAll("[\\s+]", "-");
    }

    public Product creatproduct(Product product, int vendorid) throws Exception {
        if (product == null) {
            throw new Exception("Data expected with this request.");
        }

        if (product.getDescription() == null || product.getName() == null || product.getPrice() <= 0) {
            throw new Exception("you have to fill all detail of Product");
        }

        Product existing = productrepository.findOneBySlug(generateslug(product.getName(), vendorid));

        if (existing != null) {
            throw new Exception("Product is already available");
        }

        Vendor vendor = vendorrepository.findByIdAndStatus(vendorid, VendorStatus.APPROVED);
        if (vendor == null) {
            throw new Exception("Vendor does not exist.");
        }

        Category existingCategory = categoryrepository.findByIdAndVendorIdAndIsDeleted(product.getCategory().getId(), vendorid, false);

        if (existingCategory == null) {
            throw new Exception("You are trying to add a product is an unknown category.");
        }

        product.setSlug(this.generateslug(product.getName(), vendorid));
        product.setCategory(existingCategory);
        product.setVendor(vendor);
        product.setStatus(ProductStatus.CREATED);

        return productrepository.save(product);
    }


    public Product updateproduct(int vendorid, int productid, Product product) throws Exception {
        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
        Product existedproduct = productrepository.findById(productid).get();

        if (product.getDescription() == null) {
            product.setDescription(existedproduct.getDescription());
        }

        if (product.getPrice() <= 0) {
            product.setPrice(existedproduct.getPrice());
        }

        if (product.getName() == null && product.getDescription() == null && product.getPrice() == 0) {
            throw new Exception("you didn't update nothing");
        }

        product.setSlug(generateslug(product.getName(), vendorid));
        product.setStatus(ProductStatus.UPDATED);
        return productrepository.save(product);
    }

    public List<Product> findAllProducts(int vendorid) throws Exception {
        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
        if (exisistedvendor == null) {
            throw new Exception("vendor not exist");
        }

        return productrepository.findAll();
    }

    public Product findByproductsByID(int vendorid, int productid) throws Exception {
        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
        Product existedproduct = productrepository.findById(productid).get();

        if (exisistedvendor == null && existedproduct == null) {
            throw new Exception("your file not found");
        }

        return existedproduct;
    }

    public void deleteByproductsByID(int vendorid, int productid) throws Exception {
        Vendor exisistedvendor = vendorrepository.findById(vendorid).get();
        Product existedproduct = productrepository.findById(productid).get();

        if (exisistedvendor == null && existedproduct == null) {

            throw new Exception("your file not found");
        }
        String name = existedproduct.getName();

        existedproduct.setName(name);
        productrepository.delete(existedproduct);
    }

    public Product sendForApproval(int productId) throws Exception {
        Product product = productrepository.findById(productId).orElse(null);
        if (product == null) {
            throw new Exception("Product not found");
        }
        product.setStatus(ProductStatus.WAITING_APPROVAL);
        productrepository.save(product);
        return product;
    }

    public Product approveProduct(int productId) throws Exception {
        Product product = productrepository.findById(productId).orElse(null);
        if (product == null) {
            throw new Exception("Product not found");
        }
        product.setStatus(ProductStatus.PUBLISHED);
        productrepository.save(product);
        return product;
    }

    public Product rejectProduct(int productId) throws Exception {
        Product product = productrepository.findById(productId).orElse(null);
        if (product == null) {
            throw new Exception("Product not found");
        }
        product.setStatus(ProductStatus.UNAPPROVED);
        productrepository.save(product);
        return product;
    }


}