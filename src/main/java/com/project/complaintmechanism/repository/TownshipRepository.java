package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.Township;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownshipRepository extends JpaRepository<Township, Long> {

    String join_query = "select t.* from township t, city c where t.city_id=c.id";
    String keyword_search_query = " and (c.name like :keyword% or t.name like :keyword%)";
    String city_name_search_query = " and c.name=:cityName";
    String order_by_query = " order by t.name, c.name";

    @Query(value = "select exists(select t.name from township t, city c where t.city_id=c.id and c.id=?1 and t.name=?2)", nativeQuery = true)
    int findExistsByCityIdAndTownshipName(long cityId, String townshipName);

    List<Township> findByCityIdOrderByNameAsc(long cityId);

    @Query(value = "select distinct(name) from township order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();

    // Paging
    @Query(value = join_query + keyword_search_query + order_by_query,
            countQuery = join_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Township> findByPageWithKeyword(@Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + city_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + city_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Township> findByPageWithCityNameAndKeyword(@Param("cityName") String cityName, @Param("keyword") String keyword, Pageable paging);

}
