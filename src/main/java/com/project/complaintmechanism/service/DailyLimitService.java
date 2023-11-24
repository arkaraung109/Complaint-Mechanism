package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.DailyLimit;
import com.project.complaintmechanism.model.DailyLimitModel;

public interface DailyLimitService {

    DailyLimit findFirstByOrderById();

    void update(DailyLimitModel dailyLimitModel);

}
