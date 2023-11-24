package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.Complaint;
import com.project.complaintmechanism.model.ComplaintDetailsModel;
import com.project.complaintmechanism.model.ComplaintModel;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ComplaintService {

    boolean checkLimitExceeded(int dailyLimit);

    int findComplaintCountToday();

    int findCountByAll();

    int findCountByReadStatus(boolean readStatus);

    int findCountByAcceptedStatus(int acceptedStatus);

    int findCountBySolvedStatus(boolean solvedStatus);

    Complaint findById(long id);

    Page<Complaint> findByPageForTempDeletedStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize);

    Page<Complaint> findByPageForReadStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize);

    Page<Complaint> findByPageForAcceptedStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize);

    Page<Complaint> findByPageForSolvedStatus(String status, String complaintTitleName, String date, String keyword, int pageNum, int pageSize);

    void save(ComplaintModel complaintModel) throws IOException;

    void update(ComplaintDetailsModel complaintDetailsModel) throws IOException;

    void changeReadStatus(long id, boolean readStatus);

    void changeTempDeletedStatus(long id, boolean tempDeletedStatus);

    void emptyTrash();

}
