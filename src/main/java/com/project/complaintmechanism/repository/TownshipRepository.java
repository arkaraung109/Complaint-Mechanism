package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.Township;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownshipRepository extends JpaRepository<Township, Long> {
    boolean existsByName(String name);

    Page<Township> findByCityName(String name, Pageable paging);

    Page<Township> findAllByOrderByNameAsc(Pageable paging);

    Page<Township> findByNameStartingWithIgnoreCaseOrderByNameAsc(String keyword, Pageable paging);

    Page<Township> findByNameStartingWithIgnoreCaseAndCityNameOrderByNameAsc(String keyword, String cityName, Pageable paging);
}
