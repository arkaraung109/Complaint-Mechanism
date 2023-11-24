package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.model.CompanyModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {

    boolean findExistsByCityIdAndTownshipIdAndIndustrialZoneIdAndCompanyName(long cityId, long townshipId, long industrialZoneId, String companyName);

    int findCount();

    Company findById(long id);

    List<Company> findByIndustrialZoneId(long industrialZoneId);

    List<String> findAllNames();

    Page<Company> findByPage(String cityName, String townshipName, String industrialZoneName, String keyword, int pageNum, int pageSize);

    void save(CompanyModel companyModel);

    void update(CompanyModel companyModel);

    void changeStatus(boolean status, long id);

}
