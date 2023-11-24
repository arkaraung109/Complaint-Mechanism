package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.IndustrialZoneModel;
import com.project.complaintmechanism.repository.IndustrialZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IndustrialZoneServiceImpl implements IndustrialZoneService {

    @Autowired
    private IndustrialZoneRepository industrialZoneRepository;

    @Override
    public boolean findExistsByCityIdAndTownshipIdAndIndustrialZoneName(long cityId, long townshipId, String industrialZoneName) {
        return industrialZoneRepository.findExistsByCityIdAndTownshipIdAndIndustrialZoneName(cityId, townshipId, industrialZoneName) == 1;
    }

    @Override
    public IndustrialZone findById(long id) {
        Optional<IndustrialZone> optional = industrialZoneRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<IndustrialZone> findByTownshipId(long townshipId) {
        return industrialZoneRepository.findByTownshipIdOrderByNameAsc(townshipId);
    }

    @Override
    public List<String> findAllNames() {
        return industrialZoneRepository.findAllDistinctNameByOrderByNameAsc();
    }

    @Override
    public Page<IndustrialZone> findByPage(String cityName, String townshipName, String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        boolean isCityNameEmpty = Objects.equals(cityName, "");
        boolean isTownshipNameEmpty = Objects.equals(townshipName, "");

        if(isCityNameEmpty && isTownshipNameEmpty) {
            return industrialZoneRepository.findByPageWithKeyword(keyword, paging);
        } else if(!isCityNameEmpty && isTownshipNameEmpty) {
            return industrialZoneRepository.findByPageWithCityNameAndKeyword(cityName, keyword, paging);
        } else if(isCityNameEmpty && !isTownshipNameEmpty) {
            return industrialZoneRepository.findByPageWithTownshipNameAndKeyword(townshipName, keyword, paging);
        } else {
            return industrialZoneRepository.findByPageWithCityNameAndTownshipNameAndKeyword(cityName, townshipName, keyword, paging);
        }
    }

    @Override
    public void save(IndustrialZoneModel industrialZoneModel) {
        Township township = Township.builder()
                .id(industrialZoneModel.getTownshipId())
                .build();
        IndustrialZone industrialZone = IndustrialZone.builder()
                .name(industrialZoneModel.getName())
                .township(township)
                .build();
        industrialZoneRepository.save(industrialZone);
    }

    @Override
    public void update(IndustrialZoneModel industrialZoneModel) {
        Township township = Township.builder()
                .id(industrialZoneModel.getTownshipId())
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
