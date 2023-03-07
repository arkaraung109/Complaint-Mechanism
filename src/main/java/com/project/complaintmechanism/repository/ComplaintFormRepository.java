package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.ComplaintForm;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ComplaintFormRepository extends JpaRepository<ComplaintForm, Long> {
    @Query(value = "select count(*) from complaint_form where submitted_at like ?1%", nativeQuery = true)
    int countByDate(LocalDate today);

    @Query(value = "select * from complaint_form order by submitted_at desc",
            countQuery = "select * from complaint_form order by submitted_at desc",
            nativeQuery = true)
    Page<ComplaintForm> findByPage(Pageable paging);

    @Transactional
    @Modifying
    @Query(value = "update complaint_form set read_status=?2 where id=?1", nativeQuery = true)
    void changeReadStatus(int id, boolean readStatus);

}
