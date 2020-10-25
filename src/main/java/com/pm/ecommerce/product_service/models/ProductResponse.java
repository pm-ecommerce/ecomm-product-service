package com.pm.ecommerce.product_service.models;

import com.pm.ecommerce.entities.Product;
import lombok.Data;

@Data
public class ProductResponse {


      int id;
      String name;
      double price;
      String description;
      String image;

      public ProductResponse(Product product){

             id=product.getId();
             name=product.getName();
             price=product.getPrice();
             description=product.getDescription();
          if(image!=null){

           product.getImages();
          }


      }

}
