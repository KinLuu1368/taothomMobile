package com.lab08.main.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Product> findByProductModelId(int mid) {
        return pdao.findByProductModelId(mid);
    }

    public List<Product> findByName(String name) {
        return pdao.findByName(name);
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
    
    @Override
    public List<Product> sortProducts(String sortOption) {
        List<Product> products = pdao.findAll();
        switch (sortOption) {
            case "nameAsc":
                return products.stream()
                        .sorted(Comparator.comparing(p -> p.getProductModel().getName()))
                        .collect(Collectors.toList());
            case "nameDesc":
                return products.stream()
                        .sorted(Comparator.comparing((Product p) -> p.getProductModel().getName()).reversed())
                        .collect(Collectors.toList());
            case "priceAsc":
                return products.stream()
                        .sorted(Comparator.comparing(Product::getPrice))
                        .collect(Collectors.toList());
            case "priceDesc":
                return products.stream()
                        .sorted(Comparator.comparing(Product::getPrice).reversed())
                        .collect(Collectors.toList());
            default:
                return products;
        }
    }
}
