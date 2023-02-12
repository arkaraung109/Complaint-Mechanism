package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.model.CityModel;
import com.project.complaintmechanism.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public boolean existsByName(String name) {
        return cityRepository.existsByName(name);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Optional<City> findById(long id) {
        return cityRepository.findById(id);
    }

    @Override
    public Page<City> findByPage(Pageable paging) {
        return cityRepository.findAllByOrderByNameAsc(paging);
    }

    @Override
    public Page<City> findByPageWithCityName(String cityName, Pageable paging) {
        return cityRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc(cityName, paging);
    }

    @Override
    public void saveOrUpdate(CityModel cityModel) {
        City city = City.builder()
                .id(cityModel.getId())
                .name(cityModel.getName())
                .build();
        cityRepository.save(city);
    }

    @Override
    public void deleteById(long id) {
        cityRepository.deleteById(id);
    }

}
