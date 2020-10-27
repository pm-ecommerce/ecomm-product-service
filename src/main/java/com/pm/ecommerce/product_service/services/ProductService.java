package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.*;
import com.pm.ecommerce.enums.ProductStatus;
import com.pm.ecommerce.enums.VendorStatus;
import com.pm.ecommerce.product_service.models.PagedResponse;
import com.pm.ecommerce.product_service.models.ProductResponse;
import com.pm.ecommerce.product_service.models.SingleProductResponse;
import com.pm.ecommerce.product_service.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productrepository;

    @Autowired
    CategoryRepository categoryrepository;

    @Autowired
    VendorRepository vendorrepository;

    @Autowired
    ImageRepository imagRepository;

    @Autowired
    ProductAttributeRepository attributeRepository;


    protected String generateSlug(String str, int vendorId) {
        return vendorId + "-" + str.toLowerCase().replaceAll("[\\s+]", "-");
    }

    public ProductResponse creatProduct(Product product, int vendorid) throws Exception {
        if (product == null) {
            throw new Exception("Data expected with this request.");
        }

        if (product.getDescription() == null || product.getName() == null || product.getPrice() <= 0) {
            throw new Exception("you have to fill all detail of Product");
        }

        String slug = product.getSlug() != null && product.getSlug().length() > 0 ? product.getSlug() : generateSlug(product.getName(), vendorid);
        Product existing = productrepository.findOneBySlug(slug);

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

        if (product.getSlug() == null || product.getSlug().length() == 0) {
            product.setSlug(this.generateSlug(product.getName(), vendorid));
        }

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
            Product existing = productrepository.findOneBySlug(generateSlug(product.getName(), vendorid));

            if (existing != null && existing.getId() != existingProduct.getId()) {
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

            if (product.getSlug() == null || product.getSlug().length() == 0) {
                existingProduct.setSlug(this.generateSlug(product.getName(), vendorid));
            } else {
                existingProduct.setSlug(product.getSlug());
            }
        }

        // TODO: Handle Delete, Update and Add properly
        if (product.getAttributes() != null) {
            updateAttributes(existingProduct, product);
        }

        // TODO: Handle Delete, Update and Add properly
        if (product.getImages() != null) {
            updateImages(existingProduct, product);
        }

        product.setStatus(ProductStatus.UPDATED);

        productrepository.save(existingProduct);
        Product updated = productrepository.findById(existingProduct.getId()).orElse(null);
        return new ProductResponse(updated);
    }

    private void updateAttributes(Product existing, Product update) {
        // current attributes
        Set<ProductAttribute> current = existing.getAttributes();
        Map<Integer, ProductAttribute> map = new HashMap<>();
        current.forEach(e -> {
            map.put(e.getId(), e);
        });

        // deleted attributes
        Set<Integer> updated = update.getAttributes().stream().filter(e -> e.getId() > 0).map(ProductAttribute::getId).collect(Collectors.toSet());
        current.removeIf(e -> !updated.contains(e.getId()));

        //updated attributes
        update.getAttributes().stream().filter(e -> e.getId() > 0).forEach(e -> {
            Set<Option> currentOptions = map.get(e.getId()).getOptions();
            //added and updated
            Set<Option> newOptions = e.getOptions()
                    .stream()
                    .filter(option -> option.getId() == 0 || !currentOptions.contains(option))
                    .collect(Collectors.toSet());

            //remove option
            currentOptions.removeIf(option -> !e.getOptions().contains(option));

            currentOptions.addAll(newOptions);
        });

        // new attributes
        Set<ProductAttribute> newAttributes = update.getAttributes().stream().filter(e -> e.getId() == 0).collect(Collectors.toSet());
        current.addAll(newAttributes);
    }

    private void updateImages(Product existing, Product update) {
        // current images
        Set<Image> current = existing.getImages();

        // deleted images
        Set<Long> updated = update.getImages().stream().filter(e -> e.getId() > 0).map(Image::getId).collect(Collectors.toSet());
        current.removeIf(image -> !updated.contains(image.getId()));

        // new images
        Set<Image> images = update.getImages().stream().filter(e -> e.getId() == 0).collect(Collectors.toSet());
        current.addAll(images);
    }


    public PagedResponse<ProductResponse> findAllProducts(int vendorId, int itemsPerPage, int pageNum) throws Exception {
        Vendor vendor = vendorrepository.findByIdAndStatus(vendorId, VendorStatus.APPROVED);
        if (vendor == null) {
            throw new Exception("Vendor does not exist");
        }

        if (pageNum < 1) {
            throw new Exception("Page number is invalid.");
        }

        Pageable paging = PageRequest.of(pageNum - 1, itemsPerPage);
        Page<Product> pagedResult = productrepository.findAllByVendorId(vendorId, paging);

        int totalPages = pagedResult.getTotalPages();

        List<ProductResponse> products = pagedResult.toList().stream().map(ProductResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, pageNum, itemsPerPage, products);
    }

    public List<ProductResponse> findAllProductsByStatus(ProductStatus status) throws Exception {
        return productrepository.findAllByStatus(status).stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    public SingleProductResponse findByproductsByID(int vendorid, int productid) throws Exception {
        Product existed = productrepository.findByIdAndVendorId(productid, vendorid);
        if (existed == null) {
            throw new Exception("Product does not exist.");
        }

        return new SingleProductResponse(existed);
    }

    public ProductResponse deleteByproductsByID(int vendorid, int productid) throws Exception {

        Vendor approvedvendor = vendorrepository.findByIdAndStatus(vendorid, VendorStatus.APPROVED);
        if (approvedvendor == null) {

            throw new Exception("you are not approved yet");
        }
        Product existedproduct = productrepository.findByIdAndVendorId(productid, vendorid);
        if (existedproduct == null) {

            throw new Exception("your file not found");
        }


        productrepository.delete(existedproduct);
        return new ProductResponse(existedproduct);
    }

    public ProductResponse sendForApproval(int productId, int vendorId) throws Exception {
        Product sentProduct = productrepository.findById(productId).orElse(null);

        Vendor existedvendor = vendorrepository.findByIdAndStatus(vendorId, VendorStatus.APPROVED);
        if (sentProduct == null || existedvendor == null) {
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

