package com.lab08.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab08.main.Entity.Series;

public interface SeriesDAO extends JpaRepository<Series, Integer>{
    
}
