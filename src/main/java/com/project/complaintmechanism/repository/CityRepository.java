package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByName(String name);

    List<City> findAllByOrderByNameAsc();

    Page<City> findAllByOrderByNameAsc(Pageable paging);

    City findByName(String cityName);

    Page<City> findByNameStartingWithIgnoreCaseOrderByNameAsc(String name, Pageable paging);

}
