package com.lab08.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab08.main.Entity.ProductColors;

public interface ProductColorDAO extends JpaRepository<ProductColors, Integer> {
    
}
