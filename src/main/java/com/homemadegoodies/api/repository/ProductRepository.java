package com.homemadegoodies.api.repository;

import com.homemadegoodies.api.model.Product;
import com.homemadegoodies.api.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOwnerOrderByCreatedAtDesc(User owner);
    List<Product> findAllByOrderByCreatedAtDesc();
}
