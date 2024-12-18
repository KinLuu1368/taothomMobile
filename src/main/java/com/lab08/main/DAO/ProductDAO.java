package com.lab08.main.DAO;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.lab08.main.Entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    
    @Query("SELECT p FROM Product p WHERE p.category.id=?1")
    List<Product> findByCategoryId(String cid);

    @Query("SELECT p FROM Product p WHERE p.productModel.id=?1")
    List<Product> findByProductModelId(int mid);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productModel.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Product> findByName(String name);

    @Query("SELECT p FROM Product p ORDER BY p.productModel.name ASC")
    List<Product> sortByNameAsc();

    @Query("SELECT p FROM Product p ORDER BY p.productModel.name DESC")
    List<Product> sortByNameDesc();

    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> sortByPriceAsc();

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> sortByPriceDesc();
}
