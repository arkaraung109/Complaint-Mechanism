package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.model.CityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {

    boolean existsByName(String s);

    List<City> findAll();

    Page<City> findByPage(Pageable paging);

    Page<City> findByPageWithCityName(String name, Pageable paging);

    void saveOrUpdate(CityModel cityModel);

    void deleteById(long id);

}
