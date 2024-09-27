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

import com.lab08.main.Entity.Series;
import com.lab08.main.service.SeriesService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/series")
public class SeriesRestController {
    @Autowired
    SeriesService seriesService;

    @GetMapping()
    public List<Series> getAll() {
        return seriesService.findAll();
    }

    @PostMapping()
    public Series create(@RequestBody Series series) {
        return seriesService.create(series);
    }

     @PutMapping("/{id}")
    public Series update(@PathVariable("id") Integer id,
            @RequestBody Series Series) {
        return seriesService.update(Series);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        seriesService.delete(id);
    }
}
