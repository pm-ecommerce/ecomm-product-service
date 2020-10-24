package com.pm.ecommerce.product_service.repository;

import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Integer> {

       Vendor getById(int id);

       Vendor getByBusinessName(int id);
       Vendor getByAddress(String addressname);
}
