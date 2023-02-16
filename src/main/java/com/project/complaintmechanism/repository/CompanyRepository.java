package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    String join_query = "select com.* from company com, industrial_zone i, township t, city c where com.industrial_zone_id=i.id and i.township_id=t.id and t.city_id=c.id";
    String order_by_query = "order by com.name, c.name, t.name, i.name";

    @Query(value = "select exists(" + join_query + " and c.name=?1 and t.name=?2 and i.name=?3 and com.name=?4)",
            nativeQuery = true)
    int findExistsByCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, String companyName);

    @Query(value = join_query + " " + order_by_query,
            countQuery = join_query + " " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPage(Pageable paging);

    @Query(value = join_query + " and com.name like ?1% " + order_by_query,
            countQuery = join_query + " and com.name like ?1% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCompanyName(String keyword, Pageable paging);

    @Query(value = join_query + " and c.name=?1 " + order_by_query,
            countQuery = join_query + " and c.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityName(String cityName, Pageable paging);

    @Query(value = join_query + " and t.name=?1 " + order_by_query,
            countQuery = join_query + " and t.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipName(String townshipName, Pageable paging);

    @Query(value = join_query + " and i.name=?1 " + order_by_query,
            countQuery = join_query + " and i.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithIndustrialZoneName(String industrialZoneName, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and com.name like ?2% " + order_by_query,
            countQuery = join_query + " and c.name=?1 and com.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndCompanyName(String cityName, String keyword, Pageable paging);

    @Query(value = join_query + " and t.name=?1 and com.name like ?2% " + order_by_query,
            countQuery = join_query + " and t.name=?1 and com.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipNameAndCompanyName(String townshipName, String keyword, Pageable paging);

    @Query(value = join_query + " and i.name=?1 and com.name like ?2% " + order_by_query,
            countQuery = join_query + " and i.name=?1 and com.name like ?2% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithIndustrialZoneNameAndCompanyName(String industrialZoneName, String keyword, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and t.name=?2 " + order_by_query,
            countQuery = join_query + " and c.name=?1 and t.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipName(String cityName, String townshipName, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and i.name=?2 " + order_by_query,
            countQuery = join_query + " and c.name=?1 and i.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndIndustrialZoneName(String cityName, String industrialZoneName, Pageable paging);

    @Query(value = join_query + " and t.name=?1 and i.name=?2 " + order_by_query,
            countQuery = join_query + " and t.name=?1 and i.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipNameAndIndustrialZoneName(String townshipName, String industrialZoneName, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and t.name=?2 and com.name like ?3% " + order_by_query,
            countQuery = join_query + " and c.name=?1 and t.name=?2 and com.name like ?3% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndCompanyName(String cityName, String townshipName, String keyword, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and t.name=?2 and i.name=?3 " + order_by_query,
            countQuery = join_query + " and c.name=?1 and t.name=?2 and i.name=?3 " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(String cityName, String townshipName, String industrialZoneName, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and i.name=?2 and com.name like ?3% " + order_by_query,
            countQuery = join_query + " and c.name=?1 and i.name=?2 and com.name like ?3% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndIndustrialZoneNameAndCompanyName(String cityName, String industrialZoneName, String keyword, Pageable paging);

    @Query(value = join_query + " and c.name=?1 and t.name=?2 and i.name=?3 and com.name like ?4% " + order_by_query,
            countQuery = join_query + " and c.name=?1 and t.name=?2 and i.name=?3 and com.name like ?4% " + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndCompanyName(String cityName, String townshipName, String industrialZoneName, String keyword, Pageable paging);

}
