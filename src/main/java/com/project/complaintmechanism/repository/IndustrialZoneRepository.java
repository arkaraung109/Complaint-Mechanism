package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.IndustrialZone;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustrialZoneRepository extends JpaRepository<IndustrialZone, Long> {

    String join_query = "select i.* from industrial_zone i left join township t on i.township_id=t.id left join city c on t.city_id=c.id";
    String order_by_query = "order by i.name, c.name, t.name";

    @Query(value = "select exists(select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.name=?1 and t.name=?2 and i.name=?3)",
            nativeQuery = true)
    int findExistsByCityNameAndTownshipName(String cityName, String townshipName, String industrialZoneName);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name=?1 and c.name=?2 and t.name=?3", nativeQuery = true)
    IndustrialZone findByNameAndCityNameAndTownshipName(String industrialZoneName, String cityName, String townshipName);

    List<IndustrialZone> findByTownshipNameOrderByNameAsc(String townshipName);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.name=?1 and t.name=?2 order by i.name", nativeQuery = true)
    List<IndustrialZone> findByCityNameAndTownshipName(String cityName, String townshipName);

    @Query(value = "select distinct(name) from industrial_zone order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();

    @Query(value = join_query + " " + order_by_query,
            countQuery = join_query + " " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPage(Pageable paging);

    @Query(value = join_query + " where i.name like ?1% " + order_by_query,
            countQuery = join_query + " where i.name like ?1% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithIndustrialZoneName(String keyword, Pageable paging);

    @Query(value = join_query + " where c.name=?1 " + order_by_query,
            countQuery = join_query + " where c.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityName(String cityName, Pageable paging);

    @Query(value = join_query + " where t.name=?1 " + order_by_query,
            countQuery = join_query + " where t.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithTownshipName(String townshipName, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and i.name like ?2% " + order_by_query,
            countQuery = join_query + " where c.name=?1 and i.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndIndustrialZoneName(String cityName, String keyword, Pageable paging);

    @Query(value = join_query + " where t.name=?1 and i.name like ?2% " + order_by_query,
            countQuery = join_query + " where t.name=?1 and i.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String keyword, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and t.name=?2 " + order_by_query,
            countQuery = join_query + " where c.name=?1 and t.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and t.name=?2 and i.name like ?3% " + order_by_query,
            countQuery = join_query + " where c.name=?1 and t.name=?2 and i.name like ?3% " + order_by_query,
            nativeQuery = true)
    Page<IndustrialZone> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String keyword, Pageable paging);

    @Transactional
    @Modifying
    @Query(value = "update industrial_zone set township_id=null where township_id=?1", nativeQuery = true)
    void deleteTownshipByTownshipId(long id);
}
