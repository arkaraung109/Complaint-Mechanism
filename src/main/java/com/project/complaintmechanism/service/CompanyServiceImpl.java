package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.CompanyModel;
import com.project.complaintmechanism.repository.CityRepository;
import com.project.complaintmechanism.repository.CompanyRepository;
import com.project.complaintmechanism.repository.IndustrialZoneRepository;
import com.project.complaintmechanism.repository.TownshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    TownshipRepository townshipRepository;
    @Autowired
    IndustrialZoneRepository industrialZoneRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public boolean findExistsByCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, String companyName) {
        return companyRepository.findExistsByCityNameAndTownshipNameAndIndustrialZoneName(cityName, townshipName, industrialZoneName, companyName) == 1;
    }

    @Override
    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<Company> findByIndustrialZoneName(String industrialZoneName) {
        return companyRepository.findByIndustrialZoneNameAndActiveStatusOrderByNameAsc(industrialZoneName, true);
    }

    @Override
    public List<Company> findByCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName) {
        return companyRepository.findByCityNameAndTownshipNameAndIndustrialZoneName(cityName, townshipName, industrialZoneName);
    }

    @Override
    public List<String> findAllNames() {
        return companyRepository.findAllDistinctNameByOrderByNameAsc();
    }

    @Override
    public Page<Company> findByPage(Pageable paging) {
        return companyRepository.findByPage(paging);
    }

    @Override
    public Page<Company> findByPageWithCompanyName(String keyword, Pageable paging) {
        return companyRepository.findByPageWithCompanyName(keyword, paging);
    }

    @Override
    public Page<Company> findByPageWithCityName(String cityName, Pageable paging) {
        return companyRepository.findByPageWithCityName(cityName, paging);
    }

    @Override
    public Page<Company> findByPageWithTownshipName(String townshipName, Pageable paging) {
        return companyRepository.findByPageWithTownshipName(townshipName, paging);
    }

    @Override
    public Page<Company> findByPageWithIndustrialZoneName(String industrialZoneName, Pageable paging) {
        return companyRepository.findByPageWithIndustrialZoneName(industrialZoneName, paging);
    }

    @Override
    public Page<Company> findByPageWithCityNameAndCompanyName(String cityName, String keyword, Pageable paging) {
        return companyRepository.findByPageWithCityNameAndCompanyName(cityName, keyword, paging);
    }

    @Override
    public Page<Company> findByPageWithTownshipNameAndCompanyName(String townshipName, String keyword, Pageable paging) {
        return companyRepository.findByPageWithTownshipNameAndCompanyName(townshipName, keyword, paging);
    }

    @Override
    public Page<Company> findByPageWithIndustrialZoneNameAndCompanyName(String industrialZoneName, String keyword, Pageable paging) {
        return companyRepository.findByPageWithIndustrialZoneNameAndCompanyName(industrialZoneName, keyword, paging);
    }

    @Override
    public Page<Company> findByPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging) {
        return companyRepository.findByPageWithCityNameAndTownshipName(cityName, townshipName, paging);
    }

    @Override
    public Page<Company> findByPageWithCityNameAndIndustrialZoneName(String cityName, String industrialZoneName, Pageable paging) {
        return companyRepository.findByPageWithCityNameAndIndustrialZoneName(cityName, industrialZoneName, paging);
    }

    @Override
    public Page<Company> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String industrialZoneName, Pageable paging) {
        return companyRepository.findByPageWithTownshipNameAndIndustrialZoneName(townshipName, industrialZoneName, paging);
    }

    @Override
    public Page<Company> findByPageWithCityNameAndTownshipNameAndCompanyName(String cityName, String townshipName, String keyword, Pageable paging) {
        return companyRepository.findByPageWithCityNameAndTownshipNameAndCompanyName(cityName, townshipName, keyword, paging);
    }

    @Override
    public Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, Pageable paging) {
        return companyRepository.findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(cityName, townshipName, industrialZoneName, paging);
    }

    @Override
    public Page<Company> findByPageWithCityNameAndIndustrialZoneNameAndCompanyName(String cityName, String industrialZoneName, String keyword, Pageable paging) {
        return companyRepository.findByPageWithCityNameAndIndustrialZoneNameAndCompanyName(cityName, industrialZoneName, keyword, paging);
    }

    @Override
    public Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndCompanyName(String cityName, String townshipName, String industrialZoneName, String keyword, Pageable paging) {
        return companyRepository.findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndCompanyName(cityName, townshipName, industrialZoneName, keyword, paging);
    }

    @Override
    public void saveOrUpdate(CompanyModel companyModel) {
        City city = City.builder()
                        .id(cityRepository.findByName(companyModel.getCityName()).getId())
                        .name(companyModel.getCityName())
                        .build();
        Township township = Township.builder()
                                    .id(townshipRepository.findByNameAndCityName(companyModel.getTownshipName(), companyModel.getCityName()).getId())
                                    .name(companyModel.getTownshipName())
                                    .city(city)
                                    .build();
        IndustrialZone industrialZone = IndustrialZone.builder()
                                                      .id(industrialZoneRepository.findByNameAndCityNameAndTownshipName(companyModel.getIndustrialZoneName(), companyModel.getCityName(), companyModel.getTownshipName()).getId())
                                                      .name(companyModel.getIndustrialZoneName())
                                                      .township(township)
                                                      .build();
        Company company = Company.builder()
                                 .id(companyModel.getId())
                                 .name(companyModel.getName())
                                 .industrialZone(industrialZone)
                                 .activeStatus(true)
                                 .build();
        companyRepository.save(company);
    }

    @Override
    public void changeStatus(boolean status, long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setActiveStatus(status);
            companyRepository.save(company);
        }
    }

    @Override
    public void deleteCompanyByCompanyId(long id) {
        companyRepository.deleteCompanyByCompanyId(id);
    }

}
