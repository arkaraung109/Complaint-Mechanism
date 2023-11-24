package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.model.IndustrialZoneModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IndustrialZoneService {

    boolean findExistsByCityIdAndTownshipIdAndIndustrialZoneName(long cityId, long townshipId, String industrialZoneName);

    IndustrialZone findById(long id);

    List<IndustrialZone> findByTownshipId(long townshipId);

    List<String> findAllNames();

    Page<IndustrialZone> findByPage(String cityName, String townshipName, String keyword, int pageNum, int size);

    void save(IndustrialZoneModel industrialZoneModel);

    void update(IndustrialZoneModel industrialZoneModel);

    void deleteById(long id);

}
