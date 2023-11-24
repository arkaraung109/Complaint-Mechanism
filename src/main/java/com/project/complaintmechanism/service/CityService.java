package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.model.CityModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CityService {

    boolean existsByName(String cityName);

    City findById(long id);

    List<String> findAllNames();

    List<City> findAll();

    Page<City> findByPage(String keyword, int pageNum, int pageSize);

    void save(CityModel cityModel);

    void update(CityModel cityModel);

    void deleteById(long id);
}
