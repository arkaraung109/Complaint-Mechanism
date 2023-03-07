package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintTitleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ComplaintTitleService {

    boolean existsByName(String name);

    Optional<ComplaintTitle> findById(long id);

    List<String> findAllNames();

    Page<ComplaintTitle> findByPage(Pageable paging);

    Page<ComplaintTitle> findByPageWithComplaintTitleName(String keyword, Pageable paging);

    void saveOrUpdate(ComplaintTitleModel complaintTitleModel);

    void deleteById(long id);

}
