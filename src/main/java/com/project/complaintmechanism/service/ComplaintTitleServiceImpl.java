package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintTitleModel;
import com.project.complaintmechanism.repository.ComplaintTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintTitleServiceImpl implements ComplaintTitleService {

    @Autowired
    ComplaintTitleRepository complaintTitleRepository;

    @Override
    public boolean existsByName(String complaintTitleName) {
        return complaintTitleRepository.existsByName(complaintTitleName);
    }

    @Override
    public Optional<ComplaintTitle> findById(long id) {
        return complaintTitleRepository.findById(id);
    }

    @Override
    public List<String> findAllNames() {
        return complaintTitleRepository.findAllDistinctNameByOrderByNameAsc();
    }

    @Override
    public Page<ComplaintTitle> findByPage(Pageable paging) {
        return complaintTitleRepository.findAllByOrderByNameAsc(paging);
    }

    @Override
    public Page<ComplaintTitle> findByPageWithComplaintTitleName(String keyword, Pageable paging) {
        return complaintTitleRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc(keyword, paging);
    }

    @Override
    public void saveOrUpdate(ComplaintTitleModel complaintTitleModel) {
        ComplaintTitle complaintTitle = ComplaintTitle.builder()
                                                      .id(complaintTitleModel.getId())
                                                      .name(complaintTitleModel.getName())
                                                      .build();
        complaintTitleRepository.save(complaintTitle);
    }

    @Override
    public void deleteById(long id) {
        Optional<ComplaintTitle> complaintTitleOptional = complaintTitleRepository.findById(id);
        if(complaintTitleOptional.isPresent()) {
            ComplaintTitle complaintTitle = complaintTitleOptional.get();
            complaintTitle.setComplaintFormSet(new HashSet<>());
            complaintTitleRepository.deleteById(id);
        }
    }

}
