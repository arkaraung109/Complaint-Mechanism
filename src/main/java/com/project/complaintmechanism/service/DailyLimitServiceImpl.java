package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.DailyLimit;
import com.project.complaintmechanism.model.DailyLimitModel;
import com.project.complaintmechanism.repository.DailyLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyLimitServiceImpl implements DailyLimitService {

    @Autowired
    DailyLimitRepository dailyLimitRepository;

    @Override
    public DailyLimit findFirstByOrderById() {
        DailyLimit dailyLimit = dailyLimitRepository.findFirstByOrderById();
        if(dailyLimit == null) {
            dailyLimit = DailyLimit.builder().maxLimit(15).build();
            dailyLimitRepository.save(dailyLimit);
        }
        return dailyLimit;
    }

    @Override
    public void update(DailyLimitModel dailyLimitModel) {
        DailyLimit dailyLimit = DailyLimit.builder()
                .id(dailyLimitModel.getId())
                .maxLimit(dailyLimitModel.getMaxLimit())
                .build();
        dailyLimitRepository.save(dailyLimit);
    }

}
