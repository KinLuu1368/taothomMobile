package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.Category;

public interface CategoryService {
    List<Category> findAll();

    Category create(Category category);

    Category update(Category category);

    void delete(String id);
}
