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

import java.util.List;
import java.util.Optional;

@Service
public class TownshipServiceImpl implements TownshipService {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    TownshipRepository townshipRepository;

    @Override
    public boolean findExistsByCityName(String cityName, String townshipName) {
        return (townshipRepository.findExistsByCityName(cityName, townshipName)) == 1;
    }

    @Override
    public Optional<Township> findById(long id) {
        return townshipRepository.findById(id);
    }

    @Override
    public List<Township> findByCityName(String cityName) {
        return townshipRepository.findByCityName(cityName);
    }

    @Override
    public List<Township> findAll() {
        return townshipRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Page<Township> findByPage(Pageable paging) {
        return townshipRepository.findAllByOrderByNameAscCityNameAsc(paging);
    }

    @Override
    public Page<Township> findByPageWithCityName(String cityName, Pageable paging) {
        return townshipRepository.findByCityNameOrderByNameAscCityNameAsc(cityName, paging);
    }

    @Override
    public Page<Township> findByPageWithTownshipName(String keyword, Pageable paging) {
        return townshipRepository.findByNameStartingWithIgnoreCaseOrderByNameAscCityNameAsc(keyword, paging);
    }

    @Override
    public Page<Township> findByPageWithCityNameAndTownshipName(String cityName, String keyword, Pageable paging) {
        return townshipRepository.findByNameStartingWithIgnoreCaseAndCityNameOrderByNameAscCityNameAsc(keyword, cityName, paging);
    }

    @Override
    public void saveOrUpdate(TownshipModel townshipModel) {
        City city = City.builder()
                        .id(cityRepository.findByName(townshipModel.getCityName()).getId())
                        .name(townshipModel.getCityName())
                        .build();
        Township township = Township.builder()
                                    .id(townshipModel.getId())
                                    .name(townshipModel.getName())
                                    .city(city)
                                    .build();
        townshipRepository.save(township);
    }

    @Override
    public void deleteById(long id) {
        townshipRepository.deleteById(id);
    }

}
