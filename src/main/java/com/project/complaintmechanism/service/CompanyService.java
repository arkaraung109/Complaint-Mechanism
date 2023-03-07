package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.model.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    boolean findExistsByCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, String companyName);

    Optional<Company> findById(long id);

    List<Company> findByIndustrialZoneName(String industrialZoneName);

    List<Company> findByCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName);

    List<String> findAllNames();

    Page<Company> findByPage(Pageable paging);

    Page<Company> findByPageWithCompanyName(String keyword, Pageable paging);

    Page<Company> findByPageWithCityName(String cityName, Pageable paging);

    Page<Company> findByPageWithTownshipName(String townshipName, Pageable paging);

    Page<Company> findByPageWithIndustrialZoneName(String industrialZoneName, Pageable paging);

    Page<Company> findByPageWithCityNameAndCompanyName(String cityName, String keyword, Pageable paging);

    Page<Company> findByPageWithTownshipNameAndCompanyName(String townshipName, String keyword, Pageable paging);

    Page<Company> findByPageWithIndustrialZoneNameAndCompanyName(String industrialZoneName, String keyword, Pageable paging);

    Page<Company> findByPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging);

    Page<Company> findByPageWithCityNameAndIndustrialZoneName(String cityName, String industrialZoneName, Pageable paging);

    Page<Company> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String industrialZoneName, Pageable paging);

    Page<Company> findByPageWithCityNameAndTownshipNameAndCompanyName(String cityName, String townshipName, String keyword, Pageable paging);

    Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, Pageable paging);

    Page<Company> findByPageWithCityNameAndIndustrialZoneNameAndCompanyName(String cityName, String industrialZoneName, String keyword, Pageable paging);

    Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndCompanyName(String cityName, String townshipName, String industrialZoneName, String keyword, Pageable paging);

    void saveOrUpdate(CompanyModel companyModel);

    void changeStatus(boolean status, long id);

    void deleteCompanyByCompanyId(long id);
}
