package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    String join_query = "select com.* from company com, industrial_zone i, township t, city c where com.industrial_zone_id=i.id and i.township_id=t.id and t.city_id=c.id";
    String keyword_search_query = " and (c.name like :keyword% or t.name like :keyword% or i.name like :keyword% or com.name like :keyword%)";
    String city_name_search_query = " and c.name=:cityName";
    String township_name_search_query = " and t.name=:townshipName";
    String industrial_zone_name_search_query = " and i.name=:industrialZoneName";
    String order_by_query = " order by com.name, c.name, t.name, i.name";

    @Query(value = "select exists(select com.* from company com, industrial_zone i, township t, city c where com.industrial_zone_id=i.id and i.township_id=t.id and t.city_id=c.id and c.id=?1 and t.id=?2 and i.id=?3 and com.name=?4)",
            nativeQuery = true)
    int findExistsByCityIdAndTownshipIdAndIndustrialZoneIdAndCompanyName(long cityId, long townshipId, long industrialZoneId, String companyName);

    @Query(value = "select count(*) from company", nativeQuery = true)
    int findCount();

    List<Company> findByIndustrialZoneIdAndActiveStatusOrderByNameAsc(long industrialZoneId, boolean activeStatus);

    @Query(value = "select distinct(name) from company order by name", nativeQuery = true)
    List<String> findAllDistinctNameByOrderByNameAsc();
    // Paging

    @Query(value = join_query + keyword_search_query + order_by_query,
            countQuery = join_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithKeyword(@Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + city_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + city_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndKeyword(@Param("cityName") String cityName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + township_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + township_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipNameAndKeyword(@Param("townshipName") String townshipName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithIndustrialZoneNameAndKeyword(@Param("industrialZoneName") String industrialZoneName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + city_name_search_query + township_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + city_name_search_query + township_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndKeyword(@Param("cityName") String cityName, @Param("townshipName") String townshipName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + city_name_search_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + city_name_search_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndIndustrialZoneNameAndKeyword(@Param("cityName") String cityName, @Param("industrialZoneName") String industrialZoneName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + township_name_search_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + township_name_search_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithTownshipNameAndIndustrialZoneNameAndKeyword(@Param("townshipName") String townshipName, @Param("industrialZoneName") String industrialZoneName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + city_name_search_query + township_name_search_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + city_name_search_query + township_name_search_query + industrial_zone_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Company> findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndKeyword(@Param("cityName") String cityName, @Param("townshipName") String townshipName, @Param("industrialZoneName") String industrialZoneName, @Param("keyword") String keyword, Pageable paging);

}
