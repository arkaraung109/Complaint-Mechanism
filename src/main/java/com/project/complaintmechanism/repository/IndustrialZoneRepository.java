package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.IndustrialZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustrialZoneRepository extends JpaRepository<IndustrialZone, Long> {

    @Query(value = "select exists(select i.name from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.name=?1 and t.name=?2 and i.name=?3)",
            nativeQuery = true)
    int findExistsByCityNameAndTownshipName(String cityName, String townshipName, String industrialZoneName);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findAllByOrderByNameAsc(Pageable paging);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findByNameStartingWithIgnoreCaseOrderByNameAsc(String keyword, Pageable paging);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.name=?1 order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.name=?1 order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findByCityNameOrderByNameAsc(String cityName, Pageable paging);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and t.name=?1 order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and t.name=?1 order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findByTownshipNameOrderByNameAsc(String townshipName, Pageable paging);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% and c.name=?2 order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% and c.name=?2 order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findByNameStartingWithIgnoreCaseAndCityNameOrderByNameAsc(String keyword, String cityName, Pageable paging);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% and t.name=?2 order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% and t.name=?2 order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findByNameStartingWithIgnoreCaseAndTownshipNameOrderByNameAsc(String keyword, String townshipName, Pageable paging);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.name=?1 and t.name=?2 order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and c.name=?1 and t.name=?2 order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findByCityNameAndTownshipNameOrderByNameAsc(String cityName, String townshipName, Pageable paging);

    @Query(value = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% and c.name=?2 and t.name=?3 order by i.name, c.name, t.name",
            countQuery = "select i.* from industrial_zone i, township t, city c where i.township_id=t.id and t.city_id=c.id and i.name like ?1% and c.name=?2 and t.name=?3 order by i.name, c.name, t.name",
            nativeQuery = true)
    Page<IndustrialZone> findByNameStartingWithIgnoreCaseAndCityNameAndTownshipNameOrderByNameAsc(String keyword, String cityName, String townshipName, Pageable paging);

}
