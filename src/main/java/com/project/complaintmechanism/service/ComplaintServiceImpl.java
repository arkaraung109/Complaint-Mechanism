package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.*;
import com.project.complaintmechanism.model.ComplaintDetailsModel;
import com.project.complaintmechanism.model.ComplaintModel;
import com.project.complaintmechanism.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private TownshipRepository townshipRepository;
    @Autowired
    private IndustrialZoneRepository industrialZoneRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ComplaintTitleRepository complaintTitleRepository;

    @Override
    public boolean checkLimitExceeded(int dailyLimit) {
        return complaintRepository.countByDate(LocalDate.now()) >= dailyLimit;
    }

    @Override
    public int findComplaintCountToday() {
        return complaintRepository.countByDate(LocalDate.now());
    }

    @Override
    public int findCountByAll() {
        return complaintRepository.findCountByAll();
    }

    @Override
    public int findCountByReadStatus(boolean readStatus) {
        return complaintRepository.findCountByReadStatus(readStatus);
    }

    @Override
    public int findCountByAcceptedStatus(int acceptedStatus) {
        return complaintRepository.findCountByAcceptedStatus(acceptedStatus);
    }

    @Override
    public int findCountBySolvedStatus(boolean solvedStatus) {
        return complaintRepository.findCountBySolvedStatus(solvedStatus);
    }

    @Override
    public Complaint findById(long id) {
        Optional<Complaint> optional = complaintRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Page<Complaint> findByPageForTempDeletedStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        boolean isComplaintTitleNameEmpty = Objects.equals(complaintTitleName, "");
        boolean isDateEmpty = Objects.equals(date, "");
        boolean tempDeletedStatus = Objects.equals(status, "trash");

        if(isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForTempDeletedStatusWithKeyword(tempDeletedStatus, keyword, paging);
        } else if(!isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForTempDeletedStatusWithComplaintTitleNameAndKeyword(tempDeletedStatus, complaintTitleName, keyword, paging);
        } else if(isComplaintTitleNameEmpty && !isDateEmpty) {
            return complaintRepository.findByPageForTempDeletedStatusWithDateAndKeyword(tempDeletedStatus, date, keyword, paging);
        } else {
            return complaintRepository.findByPageForTempDeletedStatusWithComplaintTitleNameAndDateAndKeyword(tempDeletedStatus, complaintTitleName, date, keyword, paging);
        }
    }

    @Override
    public Page<Complaint> findByPageForReadStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        boolean isComplaintTitleNameEmpty = Objects.equals(complaintTitleName, "");
        boolean isDateEmpty = Objects.equals(date, "");
        boolean readStatus = Objects.equals(status, "read");

        if(isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForReadStatusWithKeyword(readStatus, keyword, paging);
        } else if(!isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForReadStatusWithComplaintTitleNameAndKeyword(readStatus, complaintTitleName, keyword, paging);
        } else if(isComplaintTitleNameEmpty && !isDateEmpty) {
            return complaintRepository.findByPageForReadStatusWithDateAndKeyword(readStatus, date, keyword, paging);
        } else {
            return complaintRepository.findByPageForReadStatusWithComplaintTitleNameAndDateAndKeyword(readStatus, complaintTitleName, date, keyword, paging);
        }
    }

    @Override
    public Page<Complaint> findByPageForAcceptedStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        boolean isComplaintTitleNameEmpty = Objects.equals(complaintTitleName, "");
        boolean isDateEmpty = Objects.equals(date, "");
        int acceptedStatus = Objects.equals(status, "accepted") ? 1 : Objects.equals(status, "rejected") ? 0 : -1;

        if(isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForAcceptedStatusWithKeyword(acceptedStatus, keyword, paging);
        } else if(!isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForAcceptedStatusWithComplaintTitleNameAndKeyword(acceptedStatus, complaintTitleName, keyword, paging);
        } else if(isComplaintTitleNameEmpty && !isDateEmpty) {
            return complaintRepository.findByPageForAcceptedStatusWithDateAndKeyword(acceptedStatus, date, keyword, paging);
        } else {
            return complaintRepository.findByPageForAcceptedStatusWithComplaintTitleNameAndDateAndKeyword(acceptedStatus, complaintTitleName, date, keyword, paging);
        }
    }

    @Override
    public Page<Complaint> findByPageForSolvedStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        boolean isComplaintTitleNameEmpty = Objects.equals(complaintTitleName, "");
        boolean isDateEmpty = Objects.equals(date, "");
        boolean solvedStatus = Objects.equals(status, "solved");

        if(isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForSolvedStatusWithKeyword(solvedStatus, keyword, paging);
        } else if(!isComplaintTitleNameEmpty && isDateEmpty) {
            return complaintRepository.findByPageForSolvedStatusWithComplaintTitleNameAndKeyword(solvedStatus, complaintTitleName, keyword, paging);
        } else if(isComplaintTitleNameEmpty && !isDateEmpty) {
            return complaintRepository.findByPageForSolvedStatusWithDateAndKeyword(solvedStatus, date, keyword, paging);
        } else {
            return complaintRepository.findByPageForSolvedStatusWithComplaintTitleNameAndDateAndKeyword(solvedStatus, complaintTitleName, date, keyword, paging);
        }
    }

    @Override
    public void save(ComplaintModel complaintModel) throws IOException {
        Company company = Company.builder()
                .id(complaintModel.getCompanyId())
                .build();
        ComplaintTitle complaintTitle = ComplaintTitle.builder()
                .id(complaintModel.getComplaintTitleId())
                .build();
        Complaint complaint = Complaint.builder()
                .description(complaintModel.getDescription())
                .name(complaintModel.getName())
                .gender(complaintModel.getGender())
                .phone(complaintModel.getPhone())
                .email(complaintModel.getEmail())
                .acceptedStatus(-1)
                .ecPhoto1(complaintModel.getEcPhoto1().getBytes())
                .idCardFront(complaintModel.getIdCardFront().getBytes())
                .idCardBack(complaintModel.getIdCardBack().getBytes())
                .submittedAt(LocalDateTime.now())
                .company(company)
                .build();
        if(!complaintModel.getEcPhoto2().isEmpty()) {
            complaint.setEcPhoto2(complaintModel.getEcPhoto2().getBytes());
        }
        Set<ComplaintTitle> complaintTitleSet = new HashSet<>();
        Set<Complaint> complaintSet = new HashSet<>();
        complaintTitleSet.add(complaintTitle);
        complaintSet.add(complaint);
        complaintTitle.setComplaintSet(complaintSet);
        complaint.setComplaintTitleSet(complaintTitleSet);
        complaintRepository.save(complaint);
    }

    @Override
    public void update(ComplaintDetailsModel complaintDetailsModel) throws IOException {
        Optional<Complaint> complaintOptional = complaintRepository.findById(complaintDetailsModel.getId());
        Complaint complaintObject = complaintOptional.orElse(null);
        if(!Objects.equals(complaintObject, null)) {
            complaintObject.setRemark(complaintDetailsModel.getRemark());
            complaintObject.setAcceptedStatus(complaintDetailsModel.getAcceptedStatus());
            complaintObject.setSolvedStatus(complaintDetailsModel.isSolvedStatus());
            complaintObject.setComplaintTitleSet(complaintDetailsModel.getComplaintTitleSet());
            complaintRepository.save(complaintObject);
        }
    }

    @Override
    public void changeReadStatus(long id, boolean readStatus) {
        complaintRepository.changeReadStatus(id, readStatus);
    }

    @Override
    public void changeTempDeletedStatus(long id, boolean tempDeletedStatus) {
        complaintRepository.changeTempDeletedStatus(id, tempDeletedStatus);
    }

    @Override
    public void emptyTrash() {
        List<Complaint> complaintList = complaintRepository.findByTempDeletedStatus(true);
        complaintList.forEach(c -> {
            c.setComplaintTitleSet(new HashSet<>());
            complaintRepository.save(c);
            complaintRepository.delete(c);
        });
    }

}
