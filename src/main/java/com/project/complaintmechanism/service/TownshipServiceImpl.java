package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.TownshipModel;
import com.project.complaintmechanism.repository.TownshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TownshipServiceImpl implements TownshipService {

    @Autowired
    private TownshipRepository townshipRepository;

    @Override
    public boolean findExistsByCityIdAndTownshipName(long cityId, String townshipName) {
        return (townshipRepository.findExistsByCityIdAndTownshipName(cityId, townshipName)) == 1;
    }

    @Override
    public Township findById(long id) {
        Optional<Township> optional = townshipRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Township> findByCityId(long cityId) {
        return townshipRepository.findByCityIdOrderByNameAsc(cityId);
    }

    @Override
    public List<String> findAllNames() {
        return townshipRepository.findAllDistinctNameByOrderByNameAsc();
    }

    @Override
    public Page<Township> findByPage(String cityName, String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        boolean isCityNameEmpty = Objects.equals(cityName, "");

        if(isCityNameEmpty) {
            return townshipRepository.findByPageWithKeyword(keyword, paging);
        } else {
            return townshipRepository.findByPageWithCityNameAndKeyword(cityName, keyword, paging);
        }
    }

    @Override
    public void save(TownshipModel townshipModel) {
        City city = City.builder()
                .id(townshipModel.getCityId())
                .build();
        Township township = Township.builder()
                .name(townshipModel.getName())
                .city(city)
                .build();
        townshipRepository.save(township);
    }

    @Override
    public void update(TownshipModel townshipModel) {
        City city = City.builder()
                .id(townshipModel.getCityId())
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
