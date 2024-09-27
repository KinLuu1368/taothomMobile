package com.lab08.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.lab08.main.Entity.ProductModel;

public interface ProductModelDAO extends JpaRepository<ProductModel, Integer>{
    @Query("SELECT p FROM ProductModel p WHERE p.id=?1")
    List<ProductModel> findByCategoryId(String sid);
}
