package com.lab08.main.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lab08.main.Entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.category.id=?1")
    List<Product> findByCategoryId(String cid);
}
