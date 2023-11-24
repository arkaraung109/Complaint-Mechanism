package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.model.CityModel;
import com.project.complaintmechanism.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public boolean existsByName(String cityName) {
        return cityRepository.existsByName(cityName);
    }

    @Override
    public City findById(long id) {
        Optional<City> optional = cityRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<String> findAllNames() {
        return cityRepository.findAllDistinctNameByOrderByNameAsc();
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Page<City> findByPage(String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        return cityRepository.findByPageWithKeyword(keyword, paging);
    }

    @Override
    public void save(CityModel cityModel) {
        City city = City.builder()
                .name(cityModel.getName())
                .build();
        cityRepository.save(city);
    }

    @Override
    public void update(CityModel cityModel) {
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
