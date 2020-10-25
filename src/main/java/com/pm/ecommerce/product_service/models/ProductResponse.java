package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.Category;
import com.pm.ecommerce.entities.Product;
import com.pm.ecommerce.enums.ProductStatus;
import lombok.Data;

@Data
public class ProductResponse {


      int id;
      String name;
      double price;
      ProductStatus status;
      Category category;
      CategoryResponse response;




      public ProductResponse(Product product){

             id=product.getId();
             name=product.getName();
             price=product.getPrice();

             status=product.getStatus();
           response=new CategoryResponse(product.getCategory());





      }

}
