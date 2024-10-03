package com.lab08.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lab08.main.Entity.ProductCapacities;

import java.util.List;

public interface ProductCapacityDAO extends JpaRepository<ProductCapacities, Integer> {
    @Query("SELECT p FROM ProductCapacities p WHERE p.productModel.id=?1")
    List<ProductCapacities> findByPM(Integer id);
}
