package com.lab08.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab08.main.Entity.Category;

public interface CategoryDAO extends JpaRepository<Category, String> {

}
