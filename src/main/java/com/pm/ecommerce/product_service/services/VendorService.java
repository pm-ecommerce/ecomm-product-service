package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.product_service.models.VendorResponse;
import com.pm.ecommerce.product_service.repositories.UserRepositories;
import com.pm.ecommerce.product_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorrepository;

    public VendorResponse updatevendorinformation(Vendor vendor, int vendorid) throws Exception{

        Vendor findvendor=vendorrepository.findById(vendorid).orElse(null);
        if(findvendor==null){

            throw new Exception("vendor not found");
        }
        if(vendor.getPassword()==null){

            throw new Exception("you did not fill the pssword");
        }
        if(!findvendor.getPassword().equals(vendor.getPassword())){

            findvendor.setPassword(vendor.getPassword());

        }
        else{
            throw new Exception("you insert the previous password");
        }
        vendorrepository.save(findvendor);


        return new VendorResponse(findvendor);
    }

}
