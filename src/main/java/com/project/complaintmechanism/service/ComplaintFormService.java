package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.ComplaintForm;
import com.project.complaintmechanism.model.ComplaintFormModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ComplaintFormService {

    int findComplaintFormCountToday();

    boolean checkLimitExceeded(int dailyLimit);

    Page<ComplaintForm> findByPage(String status, Pageable paging);

    Page<ComplaintForm> findByPageWithCompanyName(String status, String companyName, Pageable paging);

    Page<ComplaintForm> findByPageWithComplaintTitleName(String status, String complaintTitleName, Pageable paging);

    Page<ComplaintForm> findByPageWithDate(String status, String date, Pageable paging);

    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleName(String status, String companyName, String complaintTitleName, Pageable paging);

    Page<ComplaintForm> findByPageWithCompanyNameAndDate(String status, String companyName, String date, Pageable paging);

    Page<ComplaintForm> findByPageWithComplaintTitleNameAndDate(String status, String complaintTitleName, String date, Pageable paging);

    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameAndDate(String status, String companyName, String complaintTitleName, String date, Pageable paging);

    void save(ComplaintFormModel complaintFormModel) throws IOException;

    void changeReadStatus(int id, boolean status);

    void changeTempDeletedStatus(int id, boolean status);

    void emptyTrash();

}
