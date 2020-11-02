package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositories extends JpaRepository<Employee,Integer> {
}
