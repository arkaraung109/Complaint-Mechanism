package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.IndustrialZoneModel;
import com.project.complaintmechanism.repository.IndustrialZoneRepository;
import com.project.complaintmechanism.repository.TownshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndustrialZoneServiceImpl implements IndustrialZoneService {

    @Autowired
    TownshipRepository townshipRepository;
    @Autowired
    IndustrialZoneRepository industrialZoneRepository;

    @Override
    public boolean findExistsByCityNameAndTownshipName(String cityName, String townshipName, String industrialZoneName) {
        return industrialZoneRepository.findExistsByCityNameAndTownshipName(cityName, townshipName, industrialZoneName) == 1;
    }

    @Override
    public Optional<IndustrialZone> findById(long id) {
        return industrialZoneRepository.findById(id);
    }

    @Override
    public Page<IndustrialZone> findByPage(Pageable paging) {
        return industrialZoneRepository.findAllByOrderByNameAsc(paging);
    }

    @Override
    public Page<IndustrialZone> findByPageWithIndustrialZoneName(String keyword, Pageable paging) {
        return industrialZoneRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc(keyword, paging);
    }

    @Override
    public Page<IndustrialZone> findByPageWithCityName(String cityName, Pageable paging) {
        return industrialZoneRepository.findByCityNameOrderByNameAsc(cityName, paging);
    }

    @Override
    public Page<IndustrialZone> findByPageWithTownshipName(String townshipName, Pageable paging) {
        return industrialZoneRepository.findByTownshipNameOrderByNameAsc(townshipName, paging);
    }

    @Override
    public Page<IndustrialZone> findByPageWithCityNameAndIndustrialZoneName(String cityName, String keyword, Pageable paging) {
        return industrialZoneRepository.findByNameStartingWithIgnoreCaseAndCityNameOrderByNameAsc(keyword, cityName, paging);
    }

    @Override
    public Page<IndustrialZone> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String keyword, Pageable paging) {
        return industrialZoneRepository.findByNameStartingWithIgnoreCaseAndTownshipNameOrderByNameAsc(keyword, townshipName, paging);
    }

    @Override
    public Page<IndustrialZone> findbyPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging) {
        return industrialZoneRepository.findByCityNameAndTownshipNameOrderByNameAsc(cityName, townshipName, paging);
    }

    @Override
    public Page<IndustrialZone> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String keyword, Pageable paging) {
        return industrialZoneRepository.findByNameStartingWithIgnoreCaseAndCityNameAndTownshipNameOrderByNameAsc(keyword, cityName, townshipName, paging);
    }

    @Override
    public void saveOrUpdate(IndustrialZoneModel industrialZoneModel) {
        Township township = Township.builder()
                                    .id(townshipRepository.findByNameAndCityName(industrialZoneModel.getTownshipName(), industrialZoneModel.getCityName()).getId())
                                    .name(industrialZoneModel.getTownshipName())
                                    .build();

        IndustrialZone industrialZone = IndustrialZone.builder()
                                                      .id(industrialZoneModel.getId())
                                                      .name(industrialZoneModel.getName())
                                                      .township(township)
                                                      .build();
        industrialZoneRepository.save(industrialZone);
    }

    @Override
    public void deleteById(long id) {
        industrialZoneRepository.deleteById(id);
    }

}
