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

    @Query(value = "select count(*) from complaint_title",
            nativeQuery = true)
    int findCount();

    @Query(value = "select distinct(name) from complaint_title order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();

    @Query(value = "select * from complaint_title where name like ?1% order by name",
            countQuery = "select * from complaint_title where name like ?1% order by name",
            nativeQuery = true)
    Page<ComplaintTitle> findByPageWithKeyword(String keyword, Pageable paging);

}
