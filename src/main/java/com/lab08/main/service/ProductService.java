package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.Product;

public interface ProductService {
    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findByCategoryId(String cid);

    List<Product> findByProductModelId(int modelId);

    List<Product> findByName(String name);

    Product create(Product product);

    Product update(Product product);

    void delete(Integer id);

}
