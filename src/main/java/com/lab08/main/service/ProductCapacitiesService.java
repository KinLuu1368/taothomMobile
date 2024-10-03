package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.ProductCapacities;

public interface ProductCapacitiesService {
    List<ProductCapacities> findAll();

    ProductCapacities findById(Integer id);

    ProductCapacities create(ProductCapacities product);

    ProductCapacities update(ProductCapacities product);

    void delete(Integer id);

    List<ProductCapacities> findByPM(Integer id);
}
