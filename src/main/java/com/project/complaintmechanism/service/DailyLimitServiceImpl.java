package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.DailyLimit;
import com.project.complaintmechanism.repository.DailyLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyLimitServiceImpl implements DailyLimitService {

    @Autowired
    DailyLimitRepository dailyLimitRepository;

    @Override
    public DailyLimit findFirstByOrderById() {
        if(dailyLimitRepository.findFirstByOrderById() == null) {
            dailyLimitRepository.save(DailyLimit.builder().maxLimit(15).build());
        }
        return dailyLimitRepository.findFirstByOrderById();
    }

    @Override
    public void save(int maxLimit) {
        DailyLimit dailyLimit = DailyLimit.builder()
                                          .id(1)
                                          .maxLimit(maxLimit)
                                          .build();
        dailyLimitRepository.save(dailyLimit);
    }

}
