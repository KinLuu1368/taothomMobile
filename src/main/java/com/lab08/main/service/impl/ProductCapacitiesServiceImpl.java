package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.ProductCapacityDAO;
import com.lab08.main.Entity.ProductCapacities;
import com.lab08.main.service.ProductCapacitiesService;

@Service
public class ProductCapacitiesServiceImpl implements ProductCapacitiesService {
    @Autowired
    ProductCapacityDAO dao;

    public List<ProductCapacities> findAll() {
        return dao.findAll();
    }

    public ProductCapacities findById(Integer id) {
        return dao.findById(id).get();
    }

    public ProductCapacities create(ProductCapacities product) {
        return dao.save(product);
    }

    @Override
    public ProductCapacities update(ProductCapacities product) {
        return dao.save(product);
    }

    @Override
    public void delete(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public List<ProductCapacities> findByPM(Integer pm) {
        return dao.findByPM(pm);
    }
}
