package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.*;
import com.project.complaintmechanism.model.ComplaintFormModel;
import com.project.complaintmechanism.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class ComplaintFormServiceImpl implements ComplaintFormService {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    TownshipRepository townshipRepository;
    @Autowired
    IndustrialZoneRepository industrialZoneRepository;
    @Autowired
    ComplaintFormRepository complaintFormRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ComplaintTitleRepository complaintTitleRepository;

    @Override
    public int findComplaintFormCountToday() {
        LocalDate today = LocalDate.now();
        int count = complaintFormRepository.countByDate(today);
        return count;
    }

    @Override
    public boolean checkLimitExceeded(int dailyLimit) {
        LocalDate today = LocalDate.now();
        int count = complaintFormRepository.countByDate(today);
        return count >= dailyLimit;
    }

    @Override
    public Page<ComplaintForm> findByPage(Pageable paging) {
        return complaintFormRepository.findByPage(paging);
    }

    @Override
    public void save(ComplaintFormModel complaintFormModel) throws IOException {
        City city = City.builder()
                .id(cityRepository.findByName(complaintFormModel.getCityName()).getId())
                .name(complaintFormModel.getCityName())
                .build();
        Township township = Township.builder()
                .id(townshipRepository.findByNameAndCityName(complaintFormModel.getTownshipName(), complaintFormModel.getCityName()).getId())
                .name(complaintFormModel.getTownshipName())
                .city(city)
                .build();
        IndustrialZone industrialZone = IndustrialZone.builder()
                .id(industrialZoneRepository.findByNameAndCityNameAndTownshipName(complaintFormModel.getIndustrialZoneName(), complaintFormModel.getCityName(), complaintFormModel.getTownshipName()).getId())
                .name(complaintFormModel.getIndustrialZoneName())
                .township(township)
                .build();
        Company company = Company.builder()
                .id(companyRepository.findByNameAndCityNameAndTownshipNameAndIndustrialZoneName(complaintFormModel.getCompanyName(), complaintFormModel.getCityName(), complaintFormModel.getTownshipName(), complaintFormModel.getIndustrialZoneName()).getId())
                .name(complaintFormModel.getCompanyName())
                .build();
        ComplaintTitle complaintTitle = ComplaintTitle.builder()
                .id(complaintTitleRepository.findByName(complaintFormModel.getComplaintTitleName()).getId())
                .build();

        ComplaintForm complaintForm = ComplaintForm.builder()
                .description(complaintFormModel.getDescription())
                .name(complaintFormModel.getName())
                .gender(complaintFormModel.getGender())
                .phone(complaintFormModel.getPhone())
                .email(complaintFormModel.getEmail())
                .acceptedStatus(-1)
                .ecPhoto1(complaintFormModel.getEcPhoto1().getBytes())
                .ecPhoto2(complaintFormModel.getEcPhoto2().getBytes())
                .idCardFront(complaintFormModel.getIdCardFront().getBytes())
                .idCardBack(complaintFormModel.getIdCardBack().getBytes())
                .submittedAt(LocalDateTime.now())
                .company(company)
                .build();
        complaintTitle.setComplaintFormSet(new HashSet<>());
        complaintForm.setComplaintTitleSet(new HashSet<>());
        complaintTitle.getComplaintFormSet().add(complaintForm);
        complaintForm.getComplaintTitleSet().add(complaintTitle);

        complaintFormRepository.save(complaintForm);
    }

    @Override
    public void changeReadStatus(int id, boolean readStatus) {
        complaintFormRepository.changeReadStatus(id, readStatus);
    }

}
