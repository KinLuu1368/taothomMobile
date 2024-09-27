package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.Series;

public interface SeriesService {
    List<Series> findAll();

    Series create(Series series);

    Series update(Series series);

    void delete(Integer id);
}
