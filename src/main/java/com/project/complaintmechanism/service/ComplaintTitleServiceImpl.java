package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintTitleModel;
import com.project.complaintmechanism.repository.ComplaintTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintTitleServiceImpl implements ComplaintTitleService {

    @Autowired
    private ComplaintTitleRepository complaintTitleRepository;

    @Override
    public boolean existsByName(String complaintTitleName) {
        return complaintTitleRepository.existsByName(complaintTitleName);
    }

    @Override
    public int findCount() {
        return complaintTitleRepository.findCount();
    }

    @Override
    public ComplaintTitle findById(long id) {
        Optional<ComplaintTitle> optional = complaintTitleRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<String> findAllNames() {
        return complaintTitleRepository.findAllDistinctNameByOrderByNameAsc();
    }

    @Override
    public List<ComplaintTitle> findAll() {
        return complaintTitleRepository.findAll();
    }

    @Override
    public Page<ComplaintTitle> findByPage(String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        return complaintTitleRepository.findByPageWithKeyword(keyword, paging);
    }

    @Override
    public void save(ComplaintTitleModel complaintTitleModel) {
        ComplaintTitle complaintTitle = ComplaintTitle.builder()
                .name(complaintTitleModel.getName())
                .build();
        complaintTitleRepository.save(complaintTitle);
    }

    @Override
    public void update(ComplaintTitleModel complaintTitleModel) {
        ComplaintTitle complaintTitle = ComplaintTitle.builder()
                .id(complaintTitleModel.getId())
                .name(complaintTitleModel.getName())
                .build();
        complaintTitleRepository.save(complaintTitle);
    }

    @Override
    public void deleteById(long id) {
        complaintTitleRepository.deleteById(id);
    }

}
