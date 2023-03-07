package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    String join_query = "select com.* from company com left join industrial_zone i on com.industrial_zone_id=i.id left join township t on i.township_id=t.id left join city c on t.city_id=c.id";

    String order_by_query = "order by com.name, c.name, t.name, i.name";

    @Query(value = "select exists(select com.* from company com, industrial_zone i, township t, city c where com.industrial_zone_id=i.id and i.township_id=t.id and t.city_id=c.id and c.name=?1 and t.name=?2 and i.name=?3 and com.name=?4)",
            nativeQuery = true)
    int findExistsByCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, String companyName);

    @Query(value = "select com.* from company com, industrial_zone i, township t, city c where com.industrial_zone_id=i.id and i.township_id=t.id and t.city_id=c.id and com.name=?1 and c.name=?2 and t.name=?3 and i.name=?4 and com.active_status=true", nativeQuery = true)
    Company findByNameAndCityNameAndTownshipNameAndIndustrialZoneName(String companyName, String cityName, String townshipName, String industrialZoneName);

    List<Company> findByIndustrialZoneNameAndActiveStatusOrderByNameAsc(String industrialZoneName, boolean activeStatus);

    @Query(value = "select com.* from company com, industrial_zone i, township t, city c where com.industrial_zone_id=i.id and i.township_id=t.id and t.city_id=c.id and c.name=?1 and t.name=?2 and i.name=?3 and com.active_status=true order by com.name", nativeQuery = true)
    List<Company> findByCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName);

    @Query(value = "select distinct(name) from company order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();

    @Query(value = join_query + " " + order_by_query,
            countQuery = join_query + " " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPage(Pageable paging);

    @Query(value = join_query + " where com.name like ?1% " + order_by_query,
            countQuery = join_query + " where com.name like ?1% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCompanyName(String keyword, Pageable paging);

    @Query(value = join_query + " where c.name=?1 " + order_by_query,
            countQuery = join_query + " where c.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityName(String cityName, Pageable paging);

    @Query(value = join_query + " where t.name=?1 " + order_by_query,
            countQuery = join_query + " where t.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipName(String townshipName, Pageable paging);

    @Query(value = join_query + " where i.name=?1 " + order_by_query,
            countQuery = join_query + " where i.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithIndustrialZoneName(String industrialZoneName, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and com.name like ?2% " + order_by_query,
            countQuery = join_query + " where c.name=?1 and com.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndCompanyName(String cityName, String keyword, Pageable paging);

    @Query(value = join_query + " where t.name=?1 and com.name like ?2% " + order_by_query,
            countQuery = join_query + " where t.name=?1 and com.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipNameAndCompanyName(String townshipName, String keyword, Pageable paging);

    @Query(value = join_query + " where i.name=?1 and com.name like ?2% " + order_by_query,
            countQuery = join_query + " where i.name=?1 and com.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithIndustrialZoneNameAndCompanyName(String industrialZoneName, String keyword, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and t.name=?2 " + order_by_query,
            countQuery = join_query + " where c.name=?1 and t.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and i.name=?2 " + order_by_query,
            countQuery = join_query + " where c.name=?1 and i.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndIndustrialZoneName(String cityName, String industrialZoneName, Pageable paging);

    @Query(value = join_query + " where t.name=?1 and i.name=?2 " + order_by_query,
            countQuery = join_query + " where t.name=?1 and i.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String industrialZoneName, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and t.name=?2 and com.name like ?3% " + order_by_query,
            countQuery = join_query + " where c.name=?1 and t.name=?2 and com.name like ?3% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndCompanyName(String cityName, String townshipName, String keyword, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and t.name=?2 and i.name=?3 " + order_by_query,
            countQuery = join_query + " where c.name=?1 and t.name=?2 and i.name=?3 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and i.name=?2 and com.name like ?3% " + order_by_query,
            countQuery = join_query + " where c.name=?1 and i.name=?2 and com.name like ?3% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndIndustrialZoneNameAndCompanyName(String cityName, String industrialZoneName, String keyword, Pageable paging);

    @Query(value = join_query + " where c.name=?1 and t.name=?2 and i.name=?3 and com.name like ?4% " + order_by_query,
            countQuery = join_query + " where c.name=?1 and t.name=?2 and i.name=?3 and com.name like ?4% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndCompanyName(String cityName, String townshipName, String industrialZoneName, String keyword, Pageable paging);

    @Transactional
    @Modifying
    @Query(value = "update company set industrial_zone_id=null where industrial_zone_id=?1", nativeQuery = true)
    void deleteCompanyByCompanyId(long id);
}
