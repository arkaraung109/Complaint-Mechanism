package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.TownshipModel;
import com.project.complaintmechanism.repository.CityRepository;
import com.project.complaintmechanism.repository.TownshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TownshipServiceImpl implements TownshipService {
    @Autowired
    CityRepository cityRepository;
    @Autowired
    TownshipRepository townshipRepository;


    @Override
    public boolean existsByName(String s) {
        return townshipRepository.existsByName(s);
    }

    @Override
    public Page<Township> findByPage(Pageable paging) {
        return townshipRepository.findAllByOrderByNameAsc(paging);
    }

    @Override
    public Page<Township> findByPageWithCityName(String cityName, Pageable paging) {
        return townshipRepository.findByCityName(cityName, paging);
    }

    @Override
    public Page<Township> findByPageWithTownshipName(String keyword, Pageable paging) {
        return townshipRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc(keyword, paging);
    }

    @Override
    public Page<Township> findByPageWithCityNameAndTownshipName(String cityName, String keyword, Pageable paging) {
        return townshipRepository.findByNameStartingWithIgnoreCaseAndCityNameOrderByNameAsc(keyword, cityName, paging);
    }

    @Override
    public void save(TownshipModel townshipModel) {
        City city = City.builder()
                        .id(cityRepository.findByName(townshipModel.getCityName()).getId())
                        .name(townshipModel.getCityName())
                        .build();
        System.out.println(city.getId());
        Township township = Township.builder()
                                    .id(townshipModel.getId())
                                    .name(townshipModel.getName())
                                    .city(city)
                                    .build();
        townshipRepository.save(township);
    }
}
