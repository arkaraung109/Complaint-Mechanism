package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.model.IndustrialZoneModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IndustrialZoneService {

    boolean findExistsByCityNameAndTownshipName(String cityName, String townshipName, String industrialZoneName);

    Optional<IndustrialZone> findById(long id);

    List<IndustrialZone> findByTownshipName(String townshipName);

    List<IndustrialZone> findByCityNameAndTownshipName(String cityName, String townshipName);

    List<String> findAllNames();

    Page<IndustrialZone> findByPage(Pageable paging);

    Page<IndustrialZone> findByPageWithIndustrialZoneName(String keyword, Pageable paging);

    Page<IndustrialZone> findByPageWithCityName(String cityName, Pageable paging);

    Page<IndustrialZone> findByPageWithTownshipName(String townshipName, Pageable paging);

    Page<IndustrialZone> findByPageWithCityNameAndIndustrialZoneName(String cityName, String keyword, Pageable paging);

    Page<IndustrialZone> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String keyword, Pageable paging);

    Page<IndustrialZone> findByPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging);

    Page<IndustrialZone> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String keyword, Pageable paging);

    void saveOrUpdate(IndustrialZoneModel industrialZoneModel);

    void deleteById(long id);

    void deleteTownshipByTownshipId(long id);

}
