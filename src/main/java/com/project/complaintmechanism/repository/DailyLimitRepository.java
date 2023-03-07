package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.DailyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyLimitRepository extends JpaRepository<DailyLimit, Long> {

    DailyLimit findFirstByOrderById();

}
