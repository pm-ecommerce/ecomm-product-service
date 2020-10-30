package com.pm.ecommerce.product_service.repositories;

import com.pm.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<User,Integer> {
}
