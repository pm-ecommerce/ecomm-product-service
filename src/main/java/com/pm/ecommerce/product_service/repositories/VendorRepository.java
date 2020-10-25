package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    Vendor findByIdAndStatus(int id, VendorStatus status);

    Vendor getByBusinessName(int id);

    Vendor getByAddress(String addressname);
}
