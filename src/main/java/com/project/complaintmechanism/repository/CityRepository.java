package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByName(String cityName);

    City findByName(String cityName);

    @Query(value = "select distinct(name) from city order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();

    Page<City> findAllByOrderByNameAsc(Pageable paging);

    Page<City> findByNameStartingWithIgnoreCaseOrderByNameAsc(String cityName, Pageable paging);

}
