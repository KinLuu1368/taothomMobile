package com.lab08.main.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab08.main.Entity.Category;
import com.lab08.main.service.CategoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/categories")
public class CategoryRestController {
    @Autowired
    CategoryService cateoryService;

    @GetMapping()
    public List<Category> getAll() {
        return cateoryService.findAll();
    }

    @PostMapping()
    public Category create(@RequestBody Category category) {
        return cateoryService.create(category);
    }
}
