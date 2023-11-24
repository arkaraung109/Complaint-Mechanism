package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.IndustrialZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustrialZoneRepository extends JpaRepository<IndustrialZone, Long> {

    String join_query = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id";
    String keyword_search_query = " and (c.name like :keyword% or t.name like :keyword% or i.name like :keyword%)";
    String city_name_search_query = " and c.name=:cityName";
    String township_name_search_query = " and t.name=:townshipName";
    String order_by_query = " order by i.name, c.name, t.name";

    @Query(value = "select exists(select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.id=?1 and t.id=?2 and i.name=?3)",
            nativeQuery = true)
    int findExistsByCityIdAndTownshipIdAndIndustrialZoneName(long cityId, long townshipId, String industrialZoneName);

    List<IndustrialZone> findByTownshipIdOrderByNameAsc(long townshipId);

    @Query(value = "select distinct(name) from industrial_zone order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();

    // Paging
    @Query(value = join_query + keyword_search_query + order_by_query,
            countQuery = join_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithKeyword(@Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + city_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + city_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndKeyword(@Param("cityName") String cityName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + township_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + township_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithTownshipNameAndKeyword(@Param("townshipName") String townshipName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + city_name_search_query + township_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + city_name_search_query + township_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndTownshipNameAndKeyword(@Param("cityName") String cityName, @Param("townshipName") String townshipName, @Param("keyword") String keyword, Pageable paging);

}
