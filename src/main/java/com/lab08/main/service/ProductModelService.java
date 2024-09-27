package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.ProductModel;

public interface ProductModelService {
    List<ProductModel> findAll();

    ProductModel create(ProductModel ProductModel);

    ProductModel update(ProductModel ProductModel);

    void delete(Integer id);

}
