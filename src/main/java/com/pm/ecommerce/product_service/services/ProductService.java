package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.enums.VendorStatus;
import com.pm.ecommerce.product_service.models.ProductResponse;
import com.pm.ecommerce.product_service.repositories.CategoryRepository;
import com.pm.ecommerce.product_service.repositories.ProductRepository;
import com.pm.ecommerce.product_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public ProductResponse creatproduct(Product product, int vendorid) throws Exception {
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

        return new ProductResponse(productrepository.save(product));
    }

    public ProductResponse updateproduct(Product product, int vendorid, int productid) throws Exception {

        if (product == null) {
            throw new Exception("Data expected with this request.");
        }

        Product pro = productrepository.findByIdAndVendorId(productid, vendorid);
        if (pro == null) {
            throw new Exception("find exception");
        }

        Product existingProduct = productrepository.findById(productid).orElse(null);
        if (existingProduct == null) {
            throw new Exception("your product was not found");
        }

        if (product.getDescription() == null && product.getName() == null && product.getPrice() <= 0) {
            throw new Exception("you have to fill all detail of Product");
        }

        if (product.getName() != null && product.getName().length() > 0) {
            Product existing = productrepository.findOneBySlug(generateslug(product.getName(), vendorid));

            if (existing != null) {
                throw new Exception("A product with the same name is already available");
            }
        }

        Vendor vendor = vendorrepository.findByIdAndStatus(vendorid, VendorStatus.APPROVED);
        if (vendor == null) {
            throw new Exception("Vendor does not exist.");
        }

        if (product.getCategory() == null) {
            Category existingCategory = categoryrepository.findByIdAndVendorIdAndIsDeleted(existingProduct.getCategory().getId(), vendorid, false);
            if (existingCategory == null) {
                throw new Exception("Category does not exist.");
            }
        } else if (product.getCategory() != null) {
            Category existingCategory = categoryrepository.findByIdAndVendorIdAndIsDeleted(product.getCategory().getId(),
                    vendorid, false);
            {
                if (existingCategory == null) {
                    throw new Exception("Category does not exist.");
                }
                existingProduct.setCategory(existingCategory);
            }
        }

        if (product.getDescription() != null && !existingProduct.getDescription().equals(product.getDescription())) {
            existingProduct.setDescription(product.getDescription());
        }

        if (product.getPrice() <= 0 && existingProduct.getPrice() != existingProduct.getPrice()) {
            existingProduct.setPrice(product.getPrice());
        }

        if (product.getName() != null && product.getName().length() > 0) {
            existingProduct.setName(product.getName());
            existingProduct.setSlug(this.generateslug(product.getName(), vendorid));
        }

        product.setStatus(ProductStatus.UPDATED);

        return new ProductResponse(productrepository.save(existingProduct));
    }

    public List<ProductResponse> findAllProducts(int vendorid) throws Exception {
        Vendor exisistedvendor = vendorrepository.findByIdAndStatus(vendorid, VendorStatus.APPROVED);
        if (exisistedvendor == null) {
            throw new Exception("vendor not exist");
        }

        return productrepository.findAllByVendorId(vendorid).stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    public List<ProductResponse> findAllProductsByStatus() {
        return productrepository.findAll().stream().map(ProductResponse::new).collect(Collectors.toList());
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

    public Product sendForApproval(int productId, int vendorId) throws Exception {
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