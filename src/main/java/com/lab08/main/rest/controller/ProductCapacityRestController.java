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

import com.lab08.main.Entity.ProductCapacities;
import com.lab08.main.service.ProductCapacitiesService;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/productcapacities")
public class ProductCapacityRestController {
    @Autowired
    ProductCapacitiesService productCapacityService;

    @GetMapping("{id}")
    public ProductCapacities getOne(@PathVariable("id") Integer id) {
        return productCapacityService.findById(id);
    }

    @GetMapping()
    public List<ProductCapacities> getAll() {
        return productCapacityService.findAll();
    }

    @PostMapping()
    public ProductCapacities create(@RequestBody ProductCapacities product) {
        return productCapacityService.create(product);
    }

    @PutMapping("/{id}")
    public ProductCapacities update(@PathVariable("id") Integer id,
            @RequestBody ProductCapacities product) {
        return productCapacityService.update(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        productCapacityService.delete(id);
    }

    @GetMapping("/byPM/{id}")
    public List<ProductCapacities> getByPM(@PathVariable("id") Integer id) {
        return productCapacityService.findByPM(id);
    }
    
}
