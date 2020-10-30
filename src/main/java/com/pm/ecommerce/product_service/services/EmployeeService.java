package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.product_service.models.EmployeeResponse;
import com.pm.ecommerce.product_service.repositories.EmployeeRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {


    @Autowired
       EmployeeRepositories emprepostories;


    public EmployeeResponse updateEmployeeInformation(Employee employee, int empid) throws Exception{

        Employee findemployee=emprepostories.findById(empid).orElse(null);
        if(findemployee==null){

            throw new Exception("employee not found");
        }
        if(employee.getPassword()==null){

            throw new Exception("you did not fill the pssword");
        }
        if(!findemployee.getPassword().equals(employee.getPassword())){

            findemployee.setPassword(employee.getPassword());

        }
        else{
            throw new Exception("you insert the previous password");
        }

          emprepostories.save(findemployee);
        return new EmployeeResponse(findemployee);
    }
}
