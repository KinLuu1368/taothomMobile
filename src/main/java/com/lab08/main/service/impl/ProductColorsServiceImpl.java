package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.ProductColorDAO;
import com.lab08.main.Entity.ProductColors;
import com.lab08.main.service.ProductColorsService;

@Service
public class ProductColorsServiceImpl implements ProductColorsService {
    @Autowired
    ProductColorDAO pdao;

    public List<ProductColors> findAll() {
        return pdao.findAll();
    }

    public ProductColors findById(Integer id) {
        return pdao.findById(id).get();
    }

    public ProductColors create(ProductColors product) {
        return pdao.save(product);
    }

    @Override
    public ProductColors update(ProductColors product) {
        return pdao.save(product);
    }

    @Override
    public void delete(Integer id) {
        pdao.deleteById(id);
    }
}
