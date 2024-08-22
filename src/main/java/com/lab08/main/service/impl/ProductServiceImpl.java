package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.ProductDAO;
import com.lab08.main.Entity.Product;
import com.lab08.main.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDAO pdao;

    public List<Product> findAll() {
        return pdao.findAll();
    }

    public Product findById(Integer id) {
        return pdao.findById(id).get();
    }

    public List<Product> findByCategoryId(String cid) {
        return pdao.findByCategoryId(cid);
    }

    public Product create(Product product) {
        return pdao.save(product);
    }

    @Override
    public Product update(Product product) {
        return pdao.save(product);
    }

    @Override
    public void delete(Integer id) {
        pdao.deleteById(id);
    }
}
