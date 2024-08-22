package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.CategoryDAO;
import com.lab08.main.Entity.Category;
import com.lab08.main.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO cdao;

    public List<Category> findAll() {
        return cdao.findAll();
    }

    @Override
    public Category create(Category Category) {
        return cdao.save(Category);
    }

    @Override
    public Category update(Category Category) {
        return cdao.save(Category);
    }

    @Override
    public void delete(String id) {
        cdao.deleteById(id);
    }
}
