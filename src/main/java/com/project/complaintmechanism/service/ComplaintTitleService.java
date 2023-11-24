package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintTitleModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ComplaintTitleService {

    boolean existsByName(String complaintTitleName);

    int findCount();

    ComplaintTitle findById(long id);

    List<String> findAllNames();

    List<ComplaintTitle> findAll();

    Page<ComplaintTitle> findByPage(String keyword, int pageNum, int pageSize);

    void save(ComplaintTitleModel complaintTitleModel);

    void update(ComplaintTitleModel complaintTitleModel);

    void deleteById(long id);

}
