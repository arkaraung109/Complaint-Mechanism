package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.TownshipModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TownshipService {

    boolean existsByName(String s);

    Page<Township> findByPage(Pageable paging);

    Page<Township> findByPageWithCityName(String cityName, Pageable paging);

    Page<Township> findByPageWithTownshipName(String keyword, Pageable paging);

    Page<Township> findByPageWithCityNameAndTownshipName(String cityName, String keyword, Pageable paging);

    void saveOrUpdate(TownshipModel townshipModel);

    void deleteById(long id);
}
