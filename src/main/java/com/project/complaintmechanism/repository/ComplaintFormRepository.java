package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.ComplaintForm;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComplaintFormRepository extends JpaRepository<ComplaintForm, Long> {

    String join_query = "select c.* from complaint_form c, company com where c.company_id=com.id and com.active_status=true";
    String join_query_complaint_title = "select c.* from complaint_form c, company com, complaint_form_title cft, complaint_title ct where c.company_id=com.id and cft.complaint_form_id=c.id and cft.complaint_title_id=ct.id and com.active_status=true";
    String order_by_query = "order by c.submitted_at desc";

    @Query(value = "select count(*) from complaint_form where submitted_at like ?1%", nativeQuery = true)
    int countByDate(LocalDate today);

    @Query(value = join_query + " and temp_deleted_status=false " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageForAll(Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and com.name=?1 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and com.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameForAll(String companyName, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and ct.name=?1 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and ct.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameForAll(String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and submitted_at like ?1% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and submitted_at like ?1% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithDateForAll(String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and com.name=?1 and ct.name=?2 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and com.name=?1 and ct.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameForAll(String companyName, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and com.name=?1 and submitted_at like ?2% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and com.name=?1 and submitted_at like ?2% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndDateForAll(String companyName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and ct.name=?1 and submitted_at like ?2% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and ct.name=?1 and submitted_at like ?2% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameAndDateForAll(String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and com.name=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and com.name=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameAndDateForAll(String companyName, String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=true " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=true " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageForTrash(Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=true and com.name=?1 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=true and com.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameForTrash(String companyName, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=true and ct.name=?1 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=true and ct.name=?1 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameForTrash(String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=true and submitted_at like ?1% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=true and submitted_at like ?1% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithDateForTrash(String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=true and com.name=?1 and ct.name=?2 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=true and com.name=?1 and ct.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameForTrash(String companyName, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=true and com.name=?1 and submitted_at like ?2% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=true and com.name=?1 and submitted_at like ?2% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndDateForTrash(String companyName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=true and ct.name=?1 and submitted_at like ?2% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=true and ct.name=?1 and submitted_at like ?2% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameAndDateForTrash(String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=true and com.name=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=true and com.name=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameAndDateForTrash(String companyName, String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and read_status=?1 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and read_status=?1 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageForReadStatus(boolean readStatus, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and read_status=?1 and com.name=?2 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and read_status=?1 and com.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameForReadStatus(boolean readStatus, String companyName, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and ct.name=?2 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and ct.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameForReadStatus(boolean readStatus, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and read_status=?1 and submitted_at like ?2% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and read_status=?1 and submitted_at like ?2% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithDateForReadStatus(boolean readStatus, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and com.name=?2 and ct.name=?3 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and com.name=?2 and ct.name=?3 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameForReadStatus(boolean readStatus, String companyName, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and read_status=?1 and com.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and read_status=?1 and com.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndDateForReadStatus(boolean readStatus, String companyName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameAndDateForReadStatus(boolean readStatus, String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and com.name=?2 and ct.name=?3 and submitted_at like ?4% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and read_status=?1 and com.name=?2 and ct.name=?3 and submitted_at like ?4% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameAndDateForReadStatus(boolean readStatus, String companyName, String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and accepted_status=?1 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and accepted_status=?1 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageForAccepted(int acceptedStatus, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameForAccepted(int acceptedStatus, String companyName, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and ct.name=?2 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and ct.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameForAccepted(int acceptedStatus, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and accepted_status=?1 and submitted_at like ?2% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and accepted_status=?1 and submitted_at like ?2% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithDateForAccepted(int acceptedStatus, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 and ct.name=?3 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 and ct.name=?3 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameForAccepted(int acceptedStatus, String companyName, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndDateForAccepted(int acceptedStatus, String companyName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameAndDateForAccepted(int acceptedStatus, String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 and ct.name=?3 and submitted_at like ?4% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and accepted_status=?1 and com.name=?2 and ct.name=?3 and submitted_at like ?4% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameAndDateForAccepted(int acceptedStatus, String companyName, String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and solved_status=?1 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and solved_status=?1 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageForSolved(boolean solvedStatus, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameForSolved(boolean solvedStatus, String companyName, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and ct.name=?2 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and ct.name=?2 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameForSolved(boolean solvedStatus, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and solved_status=?1 and submitted_at like ?2% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and solved_status=?1 and submitted_at like ?2% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithDateForSolved(boolean solvedStatus, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 and ct.name=?3 " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 and ct.name=?3 " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameForSolved(boolean solvedStatus, String companyName, String complaintTitleName, Pageable paging);

    @Query(value = join_query + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndDateForSolved(boolean solvedStatus, String companyName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and ct.name=?2 and submitted_at like ?3% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithComplaintTitleNameAndDateForSolved(boolean solvedStatus, String complaintTitleName, String date, Pageable paging);

    @Query(value = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 and ct.name=?3 and submitted_at like ?4% " + order_by_query,
            countQuery = join_query_complaint_title + " and temp_deleted_status=false and solved_status=?1 and com.name=?2 and ct.name=?3 and submitted_at like ?4% " + order_by_query,
            nativeQuery = true)
    Page<ComplaintForm> findByPageWithCompanyNameAndComplaintTitleNameAndDateForSolved(boolean solvedStatus, String companyName, String complaintTitleName, String date, Pageable paging);

    @Transactional
    @Modifying
    @Query(value = "update complaint_form set read_status=?2 where id=?1", nativeQuery = true)
    void changeReadStatus(int id, boolean status);

    @Transactional
    @Modifying
    @Query(value = "update complaint_form set temp_deleted_status=?2 where id=?1", nativeQuery = true)
    void changeTempDeletedStatus(int id, boolean status);


    List<ComplaintForm> findByTempDeletedStatus(boolean b);
}
