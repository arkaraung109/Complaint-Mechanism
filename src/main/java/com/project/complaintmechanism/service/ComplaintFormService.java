package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.ComplaintForm;
import com.project.complaintmechanism.model.ComplaintFormModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ComplaintFormService {

    int findComplaintFormCountToday();

    boolean checkLimitExceeded(int dailyLimit);

    Page<ComplaintForm> findByPage(Pageable paging);

    void save(ComplaintFormModel complaintFormModel) throws IOException;

    void changeReadStatus(int id, boolean readStatus);

}
