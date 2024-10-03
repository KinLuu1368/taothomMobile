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

import com.lab08.main.Entity.ProductColors;
import com.lab08.main.service.ProductColorsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/productcolors")
public class ProductColorRestController {
    @Autowired
    ProductColorsService productColorService;

    @GetMapping("{id}")
    public ProductColors getOne(@PathVariable("id") Integer id) {
        return productColorService.findById(id);
    }

    @GetMapping()
    public List<ProductColors> getAll() {
        return productColorService.findAll();
    }

    @PostMapping()
    public ProductColors create(@RequestBody ProductColors product) {
        return productColorService.create(product);
    }

    @PutMapping("/{id}")
    public ProductColors update(@PathVariable("id") Integer id,
            @RequestBody ProductColors product) {
        return productColorService.update(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        productColorService.delete(id);
    }
}
