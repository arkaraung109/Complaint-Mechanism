package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.IndustrialZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustrialZoneRepository extends JpaRepository<IndustrialZone, Long> {

    String join_query = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id";
    String order_by_query = "order by i.name, c.name, t.name";

    @Query(value = "select exists(" + join_query + " and c.name=?1 and t.name=?2 and i.name=?3)",
            nativeQuery = true)
    int findExistsByCityNameAndTownshipName(String cityName, String townshipName, String industrialZoneName);

    IndustrialZone findByNameAndTownshipName(String industrialZoneName, String townshipName);

    List<IndustrialZone> findByTownshipName(String townshipName);

    List<IndustrialZone> findAllByOrderByNameAsc();

    @Query(value = join_query + " " + order_by_query,
            countQuery = join_query + " " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPage(Pageable paging);

    @Query(value = join_query + " and i.name like ?1% " + order_by_query,
            countQuery = join_query + " and i.name like ?1% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithIndustrialZoneName(String keyword, Pageable paging);

    @Query(value = join_query + " and c.name=?1 " + order_by_query,
            countQuery = join_query + " and c.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityName(String cityName, Pageable paging);

    @Query(value = join_query + " and t.name=?1 " + order_by_query,
            countQuery = join_query + " and t.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithTownshipName(String townshipName, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and i.name like ?2% " + order_by_query,
            countQuery = join_query + " and c.name=?1 and i.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndIndustrialZoneName(String cityName, String keyword, Pageable paging);

    @Query(value = join_query + " and t.name=?1 and i.name like ?2% " + order_by_query,
            countQuery = join_query + " and t.name=?1 and i.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String keyword, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and t.name=?2 " + order_by_query,
            countQuery = join_query + " and c.name=?1 and t.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and t.name=?2 and i.name like ?3% " + order_by_query,
            countQuery = join_query + " and c.name=?1 and t.name=?2 and i.name like ?3% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String keyword, Pageable paging);
}
