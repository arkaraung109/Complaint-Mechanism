package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.ComplaintTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintTitleRepository extends JpaRepository<ComplaintTitle, Long> {

    boolean existsByName(String complaintTitleName);

    ComplaintTitle findByName(String complaintTitleName);

    @Query(value = "select distinct(name) from complaint_title order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();

    Page<ComplaintTitle> findAllByOrderByNameAsc(Pageable paging);

    Page<ComplaintTitle> findByNameStartingWithIgnoreCaseOrderByNameAsc(String keyword, Pageable paging);
}
