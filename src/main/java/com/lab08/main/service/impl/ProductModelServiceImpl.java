package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.ProductModelDAO;
import com.lab08.main.Entity.ProductModel;
import com.lab08.main.service.ProductModelService;

@Service
public class ProductModelServiceImpl implements ProductModelService{
    @Autowired
    ProductModelDAO dao;

    public List<ProductModel> findAll() {
        return dao.findAll();
    }

    public ProductModel create(ProductModel ProductModel) {
        return dao.save(ProductModel);
    }

    public ProductModel update(ProductModel ProductModel) {
        return dao.save(ProductModel);
    }

    @Override
    public void delete(Integer id) {
       dao.deleteById(id);
    }
}
