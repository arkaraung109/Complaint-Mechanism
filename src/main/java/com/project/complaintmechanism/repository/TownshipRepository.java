package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.Township;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownshipRepository extends JpaRepository<Township, Long> {

    @Query(value = "select exists(select t.name from township t, city c where t.city_id=c.id and c.name=?1 and t.name=?2)", nativeQuery = true)
    int findExistsByCityName(String cityName, String townshipName);

    Township findByNameAndCityName(String townshipName, String cityName);

    List<Township> findByCityName(String cityName);

    List<Township> findAllByOrderByNameAsc();

    Page<Township> findAllByOrderByNameAscCityNameAsc(Pageable paging);

    Page<Township> findByCityNameOrderByNameAscCityNameAsc(String cityName, Pageable paging);

    Page<Township> findByNameStartingWithIgnoreCaseOrderByNameAscCityNameAsc(String keyword, Pageable paging);

    Page<Township> findByNameStartingWithIgnoreCaseAndCityNameOrderByNameAscCityNameAsc(String keyword, String cityName, Pageable paging);

}
