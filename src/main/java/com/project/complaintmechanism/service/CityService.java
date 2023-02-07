package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.model.CityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {
    List<City> findAll();
    void save(CityModel cityModel);
    Boolean existsByName(String s);

    void update(CityModel cityModel);

    void deleteById(long id);


    Page<City> findByPage(Pageable paging);

    Page<City> findByPageWithCityName(String name, Pageable paging);
}
