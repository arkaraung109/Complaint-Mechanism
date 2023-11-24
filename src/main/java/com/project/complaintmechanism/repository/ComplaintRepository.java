package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.Complaint;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    String join_query = "select c.* from complaint c, company com, complaint_has_complaint_title chct, complaint_title ct where c.company_id=com.id and chct.complaint_id=c.id and chct.complaint_title_id=ct.id and com.active_status=true";
    String keyword_search_query = " and (c.name like :keyword% or com.name like :keyword% or ct.name like :keyword% or c.description like :keyword% or cast(c.submitted_at as date) like :keyword%)";
    String complaint_title_name_search_query = " and ct.name=:complaintTitleName";
    String date_search_query = " and cast(c.submitted_at as date)=:date";
    String temp_deleted_status_query = " and c.temp_deleted_status=:tempDeletedStatus";
    String not_temp_deleted_status_query = " and c.temp_deleted_status=false";
    String read_status_query = " and c.read_status=:readStatus";
    String accepted_status_query = " and c.accepted_status=:acceptedStatus";
    String solved_status_query = " and c.solved_status=:solvedStatus";
    String order_by_query = " group by c.id order by c.submitted_at desc";

    @Query(value = "select count(*) from complaint where cast(submitted_at as date)=?1", nativeQuery = true)
    int countByDate(LocalDate date);

    @Query(value = "select count(*) from complaint", nativeQuery = true)
    int findCountByAll();

    @Query(value = "select count(*) from complaint where read_status=?1", nativeQuery = true)
    int findCountByReadStatus(boolean readStatus);

    @Query(value = "select count(*) from complaint where accepted_status=?1", nativeQuery = true)
    int findCountByAcceptedStatus(int acceptedStatus);

    @Query(value = "select count(*) from complaint where solved_status=?1", nativeQuery = true)
    int findCountBySolvedStatus(boolean solvedStatus);

    // Paging For Temp Deleted Status
    @Query(value = join_query + temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForTempDeletedStatusWithKeyword(@Param("tempDeletedStatus") boolean tempDeletedStatus, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + temp_deleted_status_query + complaint_title_name_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + temp_deleted_status_query + complaint_title_name_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForTempDeletedStatusWithComplaintTitleNameAndKeyword(@Param("tempDeletedStatus") boolean tempDeletedStatus, @Param("complaintTitleName") String complaintTitleName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + temp_deleted_status_query + date_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + temp_deleted_status_query + date_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForTempDeletedStatusWithDateAndKeyword(@Param("tempDeletedStatus") boolean tempDeletedStatus, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + temp_deleted_status_query + complaint_title_name_search_query + date_search_query + keyword_search_query + order_by_query,
            countQuery = join_query + temp_deleted_status_query + complaint_title_name_search_query + date_search_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForTempDeletedStatusWithComplaintTitleNameAndDateAndKeyword(@Param("tempDeletedStatus") boolean tempDeletedStatus, @Param("complaintTitleName") String complaintTitleName, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    // Paging For Read Status
    @Query(value = join_query + read_status_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + read_status_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForReadStatusWithKeyword(@Param("readStatus") boolean readStatus, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + read_status_query + complaint_title_name_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + read_status_query + complaint_title_name_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForReadStatusWithComplaintTitleNameAndKeyword(@Param("readStatus") boolean readStatus, @Param("complaintTitleName") String complaintTitleName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + read_status_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + read_status_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForReadStatusWithDateAndKeyword(@Param("readStatus") boolean readStatus, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + read_status_query + complaint_title_name_search_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + read_status_query + complaint_title_name_search_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForReadStatusWithComplaintTitleNameAndDateAndKeyword(@Param("readStatus") boolean readStatus, @Param("complaintTitleName") String complaintTitleName, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    // Paging For Accepted Status
    @Query(value = join_query + accepted_status_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + accepted_status_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForAcceptedStatusWithKeyword(@Param("acceptedStatus") int acceptedStatus, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + accepted_status_query + complaint_title_name_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + accepted_status_query + complaint_title_name_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForAcceptedStatusWithComplaintTitleNameAndKeyword(@Param("acceptedStatus") int acceptedStatus, @Param("complaintTitleName") String complaintTitleName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + accepted_status_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + accepted_status_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForAcceptedStatusWithDateAndKeyword(@Param("acceptedStatus") int acceptedStatus, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + accepted_status_query + complaint_title_name_search_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + accepted_status_query + complaint_title_name_search_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForAcceptedStatusWithComplaintTitleNameAndDateAndKeyword(@Param("acceptedStatus") int acceptedStatus, @Param("complaintTitleName") String complaintTitleName, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    // Paging For Solved Status
    @Query(value = join_query + solved_status_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + solved_status_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForSolvedStatusWithKeyword(@Param("solvedStatus") boolean solvedStatus, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + solved_status_query + complaint_title_name_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + solved_status_query + complaint_title_name_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForSolvedStatusWithComplaintTitleNameAndKeyword(@Param("solvedStatus") boolean solvedStatus, @Param("complaintTitleName") String complaintTitleName, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + solved_status_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + solved_status_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForSolvedStatusWithDateAndKeyword(@Param("solvedStatus") boolean solvedStatus, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    @Query(value = join_query + solved_status_query + complaint_title_name_search_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            countQuery = join_query + solved_status_query + complaint_title_name_search_query + date_search_query + not_temp_deleted_status_query + keyword_search_query + order_by_query,
            nativeQuery = true)
    Page<Complaint> findByPageForSolvedStatusWithComplaintTitleNameAndDateAndKeyword(@Param("solvedStatus") boolean solvedStatus, @Param("complaintTitleName") String complaintTitleName, @Param("date") String date, @Param("keyword") String keyword, Pageable paging);

    @Transactional
    @Modifying
    @Query(value = "update complaint set read_status=?2 where id=?1", nativeQuery = true)
    void changeReadStatus(long id, boolean readStatus);

    @Transactional
    @Modifying
    @Query(value = "update complaint set temp_deleted_status=?2 where id=?1", nativeQuery = true)
    void changeTempDeletedStatus(long id, boolean tempDeletedStatus);

    List<Complaint> findByTempDeletedStatus(boolean tempDeletedStatus);

}
