package com.lab08.main.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab08.main.Entity.ProductModel;
import com.lab08.main.service.ProductModelService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/ProductModel")
public class ProductModelRestController {
    @Autowired
    ProductModelService ProductModelService;

    @GetMapping()
    public List<ProductModel> getAll() {
        return ProductModelService.findAll();
    }

    @PostMapping()
    public ProductModel create(@RequestBody ProductModel productModel) {
        return ProductModelService.create(productModel);
    }

     @PutMapping("/{id}")
    public ProductModel update(@PathVariable("id") Integer id,
            @RequestBody ProductModel productModel) {
        return ProductModelService.update(productModel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        ProductModelService.delete(id);
    }
}
