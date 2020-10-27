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

          System.out.println("*****************************1");
        if (product.getDescription() == null || product.getName() == null || product.getPrice() <= 0) {
            throw new Exception("you have to fill all detail of Product");
        }
        System.out.println("*****************************2");
        Product existing = productrepository.findOneBySlug(generateslug(product.getName(), vendorid));

        if (existing != null) {
            throw new Exception("Product is already available");
        }
        System.out.println("*****************************3");
        Vendor vendor = vendorrepository.findByIdAndStatus(vendorid, VendorStatus.APPROVED);
        if (vendor == null) {
            throw new Exception("Vendor does not exist.");
        }
        System.out.println("*****************************4");

        Category existingCategory = categoryrepository.findByIdAndVendorIdAndIsDeleted(product.getCategory().getId(), vendorid, false);

        if (existingCategory == null) {
            throw new Exception("You are trying to add a product is an unknown category.");
        }
        System.out.println("*****************************5");

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

    public List<ProductResponse> findAllProductsByStatus(ProductStatus status) throws Exception{


        return productrepository.findAllByStatus(status).stream().map(ProductResponse::new).collect(Collectors.toList());
    }

  public ProductResponse findByproductsByID(int vendorid,int productid) throws Exception {


           Product existed=productrepository.findByIdAndVendorId(productid,vendorid);
             if(existed==null){

                 throw new Exception("your file not recorded");
             }

      return new ProductResponse(existed);
    }

   public ProductResponse deleteByproductsByID(int vendorid, int productid) throws Exception {

          Vendor approvedvendor=vendorrepository.findByIdAndStatus(vendorid,VendorStatus.APPROVED);
          if(approvedvendor==null){

              throw new Exception("you are not approved yet");
          }
         Product existedproduct=productrepository.findByIdAndVendorId(productid,vendorid);
        if (existedproduct == null) {

            throw new Exception("your file not found");
        }



        productrepository.delete(existedproduct);
        return new ProductResponse(existedproduct);
    }

   public ProductResponse sendForApproval(int productId, int vendorId) throws Exception {
        Product sentProduct = productrepository.findById(productId).orElse(null);

        Vendor existedvendor=vendorrepository.findByIdAndStatus(vendorId,VendorStatus.APPROVED);
        if (sentProduct == null||existedvendor==null) {
            throw new Exception("Product not found or you are not approved vendor Yet");
        }
       sentProduct.setStatus(ProductStatus.WAITING_APPROVAL);
        productrepository.save(sentProduct);
        return new ProductResponse(sentProduct);
    }

    public ProductResponse approveProduct(int productId) throws Exception {
        Product approvedProduct = productrepository.findById(productId).orElse(null);
        if (approvedProduct == null) {
            throw new Exception("Product not found");
        }
        approvedProduct.setStatus(ProductStatus.PUBLISHED);
        productrepository.save(approvedProduct);
        return new ProductResponse(approvedProduct);
    }

   public ProductResponse rejectProduct(int productId) throws Exception {
        Product rejectedproduct = productrepository.findById(productId).orElse(null);
        if (rejectedproduct == null) {
            throw new Exception("Product not found");
        }
       rejectedproduct.setStatus(ProductStatus.UNAPPROVED);
        productrepository.save(rejectedproduct);
        return new ProductResponse(rejectedproduct);

    }

}

