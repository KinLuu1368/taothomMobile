package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.SeriesDAO;
import com.lab08.main.Entity.Series;
import com.lab08.main.service.SeriesService;

@Service
public class SeriesServiceImpl implements SeriesService {
    @Autowired
    SeriesDAO dao;

    public List<Series> findAll() {
        return dao.findAll();
    }

    public Series create(Series series) {
        return dao.save(series);
    }

    public Series update(Series series) {
        return dao.save(series);
    }

    @Override
    public void delete(Integer id) {
       dao.deleteById(id);
    }
}
