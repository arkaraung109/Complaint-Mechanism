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
import java.util.List;
import java.util.Objects;

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
        return complaintFormRepository.countByDate(today);
    }

    @Override
    public boolean checkLimitExceeded(int dailyLimit) {
        LocalDate today = LocalDate.now();
        int count = complaintFormRepository.countByDate(today);
        return count >= dailyLimit;
    }

    @Override
    public Page<ComplaintForm> findByPage(String status, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageForAll(paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageForTrash(paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageForReadStatus(read_status, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageForAccepted(accepted_status, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageForSolved(solved_status, paging);
        }
        else {
            return null;
        }

    }

    @Override
    public Page<ComplaintForm> findByPageWithCompanyName(String status, String companyName, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageWithCompanyNameForAll(companyName, paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageWithCompanyNameForTrash(companyName, paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageWithCompanyNameForReadStatus(read_status, companyName, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageWithCompanyNameForAccepted(accepted_status, companyName, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageWithCompanyNameForSolved(solved_status, companyName, paging);
        }
        else {
            return null;
        }
    }

    @Override
    public Page<ComplaintForm> findByPageWithComplaintTitleName(String status, String complaintTitleName, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageWithComplaintTitleNameForAll(complaintTitleName, paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageWithComplaintTitleNameForTrash(complaintTitleName, paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageWithComplaintTitleNameForReadStatus(read_status, complaintTitleName, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageWithComplaintTitleNameForAccepted(accepted_status, complaintTitleName, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageWithComplaintTitleNameForSolved(solved_status, complaintTitleName, paging);
        }
        else {
            return null;
        }
    }

    @Override
    public Page<ComplaintForm> findByPageWithDate(String status, String date, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageWithDateForAll(date, paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageWithDateForTrash(date, paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageWithDateForReadStatus(read_status, date, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageWithDateForAccepted(accepted_status, date, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageWithDateForSolved(solved_status, date, paging);
        }
        else {
            return null;
        }
    }

    @Override
    public Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleName(String status, String companyName, String complaintTitleName, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameForAll(companyName, complaintTitleName, paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameForTrash(companyName, complaintTitleName, paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameForReadStatus(read_status, companyName, complaintTitleName, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameForAccepted(accepted_status, companyName, complaintTitleName, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameForSolved(solved_status, companyName, complaintTitleName, paging);
        }
        else {
            return null;
        }
    }

    @Override
    public Page<ComplaintForm> findByPageWithCompanyNameAndDate(String status, String companyName, String date, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageWithCompanyNameAndDateForAll(companyName, date, paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageWithCompanyNameAndDateForTrash(companyName, date, paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageWithCompanyNameAndDateForReadStatus(read_status, companyName, date, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageWithCompanyNameAndDateForAccepted(accepted_status, companyName, date, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageWithCompanyNameAndDateForSolved(solved_status, companyName, date, paging);
        }
        else {
            return null;
        }
    }

    @Override
    public Page<ComplaintForm> findByPageWithComplaintTitleNameAndDate(String status, String complaintTitleName, String date, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageWithComplaintTitleNameAndDateForAll(complaintTitleName, date, paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageWithComplaintTitleNameAndDateForTrash(complaintTitleName, date, paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageWithComplaintTitleNameAndDateForReadStatus(read_status, complaintTitleName, date, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageWithComplaintTitleNameAndDateForAccepted(accepted_status, complaintTitleName, date, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageWithComplaintTitleNameAndDateForSolved(solved_status, complaintTitleName, date, paging);
        }
        else {
            return null;
        }
    }

    @Override
    public Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameAndDate(String status, String companyName, String complaintTitleName, String date, Pageable paging) {
        boolean allStatus = Objects.equals(status, "All");
        boolean trashStatus = Objects.equals(status, "Trash");
        boolean readStatus = Objects.equals(status, "Read")||Objects.equals(status, "Unread");
        boolean acceptedStatus = Objects.equals(status, "Accepted")||Objects.equals(status, "Rejected")||Objects.equals(status, "Pending");
        boolean solvedStatus = Objects.equals(status, "Solved")||Objects.equals(status, "Unsolved");

        if(allStatus) {
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameAndDateForAll(companyName, complaintTitleName, date, paging);
        }
        else if(trashStatus) {
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameAndDateForTrash(companyName, complaintTitleName, date, paging);
        }
        else if(readStatus) {
            boolean read_status = Objects.equals(status, "Read");
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameAndDateForReadStatus(read_status, companyName, complaintTitleName, date, paging);
        }
        else if(acceptedStatus) {
            int accepted_status = Objects.equals(status, "Pending") ? -1 : Objects.equals(status, "Rejected") ? 0 : 1;
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameAndDateForAccepted(accepted_status, companyName, complaintTitleName, date, paging);
        }
        else if(solvedStatus) {
            boolean solved_status = Objects.equals(status, "Solved");
            return complaintFormRepository.findByPageWithCompanyNameAndComplaintTitleNameAndDateForSolved(solved_status, companyName, complaintTitleName, date, paging);
        }
        else {
            return null;
        }
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
    public void changeReadStatus(int id, boolean status) {
        complaintFormRepository.changeReadStatus(id, status);
    }

    @Override
    public void changeTempDeletedStatus(int id, boolean status) {
        complaintFormRepository.changeTempDeletedStatus(id, status);
    }

    @Override
    public void emptyTrash() {
        List<ComplaintForm> complaintFormList = complaintFormRepository.findByTempDeletedStatus(true);
        complaintFormList.forEach(c -> {
            c.setComplaintTitleSet(new HashSet<>());
            complaintFormRepository.save(c);
            complaintFormRepository.delete(c);
        });
    }

}
