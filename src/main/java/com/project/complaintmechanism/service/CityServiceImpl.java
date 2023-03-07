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
    public boolean existsByName(String cityName) {
        return cityRepository.existsByName(cityName);
    }

    @Override
    public List<String> findAllNames() {
        return cityRepository.findAllDistinctNameByOrderByNameAsc();
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
    public Page<City> findByPageWithCityName(String keyword, Pageable paging) {
        return cityRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc(keyword, paging);
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
