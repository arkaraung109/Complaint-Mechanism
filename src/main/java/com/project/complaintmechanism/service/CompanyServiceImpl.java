package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.model.CompanyModel;
import com.project.complaintmechanism.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public boolean findExistsByCityIdAndTownshipIdAndIndustrialZoneIdAndCompanyName(long cityId, long townshipId, long industrialZoneId, String companyName) {
        return companyRepository.findExistsByCityIdAndTownshipIdAndIndustrialZoneIdAndCompanyName(cityId, townshipId, industrialZoneId, companyName) == 1;
    }

    @Override
    public int findCount() {
        return companyRepository.findCount();
    }

    @Override
    public Company findById(long id) {
        Optional<Company> optional = companyRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Company> findByIndustrialZoneId(long industrialZoneId) {
        return companyRepository.findByIndustrialZoneIdAndActiveStatusOrderByNameAsc(industrialZoneId, true);
    }

    @Override
    public List<String> findAllNames() {
        return companyRepository.findAllDistinctNameByOrderByNameAsc();
    }

    @Override
    public Page<Company> findByPage(String cityName, String townshipName, String industrialZoneName, String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        boolean isCityNameEmpty = Objects.equals(cityName, "");
        boolean isTownshipNameEmpty = Objects.equals(townshipName, "");
        boolean isIndustrialZoneNameEmpty = Objects.equals(industrialZoneName, "");

        if(isCityNameEmpty && isTownshipNameEmpty && isIndustrialZoneNameEmpty) {
            return companyRepository.findByPageWithKeyword(keyword, paging);
        } else if(!isCityNameEmpty && isTownshipNameEmpty && isIndustrialZoneNameEmpty) {
            return companyRepository.findByPageWithCityNameAndKeyword(cityName, keyword, paging);
        } else if(isCityNameEmpty && !isTownshipNameEmpty && isIndustrialZoneNameEmpty) {
            return companyRepository.findByPageWithTownshipNameAndKeyword(townshipName, keyword, paging);
        } else if(isCityNameEmpty && isTownshipNameEmpty && !isIndustrialZoneNameEmpty) {
            return companyRepository.findByPageWithIndustrialZoneNameAndKeyword(industrialZoneName, keyword, paging);
        } else if(!isCityNameEmpty && !isTownshipNameEmpty && isIndustrialZoneNameEmpty) {
            return companyRepository.findByPageWithCityNameAndTownshipNameAndKeyword(cityName, townshipName, keyword, paging);
        } else if(!isCityNameEmpty && isTownshipNameEmpty && !isIndustrialZoneNameEmpty) {
            return companyRepository.findByPageWithCityNameAndIndustrialZoneNameAndKeyword(cityName, industrialZoneName, keyword, paging);
        } else if(isCityNameEmpty && !isTownshipNameEmpty && !isIndustrialZoneNameEmpty) {
            return companyRepository.findByPageWithTownshipNameAndIndustrialZoneNameAndKeyword(townshipName, industrialZoneName, keyword, paging);
        } else {
            return companyRepository.findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndKeyword(cityName, townshipName, industrialZoneName, keyword, paging);
        }
    }

    @Override
    public void save(CompanyModel companyModel) {
        IndustrialZone industrialZone = IndustrialZone.builder()
                .id(companyModel.getIndustrialZoneId())
                .build();
        Company company = Company.builder()
                .name(companyModel.getName())
                .industrialZone(industrialZone)
                .activeStatus(false)
                .build();
        companyRepository.save(company);
    }

    @Override
    public void update(CompanyModel companyModel) {
        IndustrialZone industrialZone = IndustrialZone.builder()
                .id(companyModel.getIndustrialZoneId())
                .build();
        Company company = Company.builder()
                .id(companyModel.getId())
                .name(companyModel.getName())
                .industrialZone(industrialZone)
                .activeStatus(companyModel.isActiveStatus())
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

}
