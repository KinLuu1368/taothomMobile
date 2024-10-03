package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.ProductColors;

public interface ProductColorsService {
    List<ProductColors> findAll();

    ProductColors findById(Integer id);

    ProductColors create(ProductColors product);

    ProductColors update(ProductColors product);

    void delete(Integer id);

}
