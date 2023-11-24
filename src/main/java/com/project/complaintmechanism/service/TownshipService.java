package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.TownshipModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TownshipService {

    boolean findExistsByCityIdAndTownshipName(long cityId, String townshipName);

    Township findById(long id);

    List<Township> findByCityId(long cityId);

    List<String> findAllNames();

    Page<Township> findByPage(String cityName, String keyword, int pageNum, int size);

    void save(TownshipModel townshipModel);

    void update(TownshipModel townshipModel);

    void deleteById(long id);

}
