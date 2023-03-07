package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.DailyLimit;

public interface DailyLimitService {

    DailyLimit findFirstByOrderById();

    void save(int maxLimit);

}
