package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.ComplaintForm;
import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintTitleModel;
import com.project.complaintmechanism.repository.ComplaintFormRepository;
import com.project.complaintmechanism.service.ComplaintFormService;
import com.project.complaintmechanism.service.ComplaintTitleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api/complaintTitle")
public class ComplaintTitleController {

    @Autowired
    ComplaintFormService complaintFormService;
    @Autowired
    ComplaintTitleService complaintTitleService;
    @Autowired
    private ComplaintFormRepository complaintFormRepository;

    @GetMapping("/")
    public String showList(Model model) {

        model.addAttribute("complaintTitle", new ComplaintTitleModel());
        return getByPage(model, null, 1, 5);

    }

    @GetMapping("/page")
    public String getPaginated(Model model, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        model.addAttribute("complaintTitle", new ComplaintTitleModel());
        return getByPage(model, keyword, page, size);

    }

    public String getByPage(Model model, String keyword, int page, int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        Page<ComplaintTitle> complaintTitlePage;

        if(keyword == null || keyword.equalsIgnoreCase("")) {
            complaintTitlePage = complaintTitleService.findByPage(paging);
        }
        else {
            complaintTitlePage = complaintTitleService.findByPageWithComplaintTitleName(keyword, paging);
            model.addAttribute("keyword", keyword);
        }

        List<ComplaintTitle> complaintTitleList = complaintTitlePage.getContent();
        model.addAttribute("complaintTitleList", complaintTitleList);
        model.addAttribute("currentPage", complaintTitlePage.getNumber() + 1);
        model.addAttribute("totalItems", complaintTitlePage.getTotalElements());
        model.addAttribute("totalPages", complaintTitlePage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "complaint_title_list";

    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("complaintTitle") ComplaintTitleModel complaintTitleModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (!result.hasErrors()) {
            complaintTitleService.saveOrUpdate(complaintTitleModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/complaintTitle/";
        }
        return getByPage(model, null, 1, 5);

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("complaintTitle") ComplaintTitleModel complaintTitleModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        Optional<ComplaintTitle> complaintTitle = complaintTitleService.findById(id);

        if(complaintTitle.isPresent()) {
            if(!result.hasErrors())  {
                complaintTitleModel.setId(id);
                complaintTitleService.saveOrUpdate(complaintTitleModel);
                redirectAttributes.addFlashAttribute("updated_success", true);
                return "redirect:/api/complaintTitle/";
            }

            return getByPage(model, null, 1, 5);
        }
        else {
            redirectAttributes.addFlashAttribute("complaint_title_not_found", true);
            return "redirect:/api/complaintTitle/";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {

        Optional<ComplaintTitle> complaintTitle = complaintTitleService.findById(id);

        if(complaintTitle.isPresent()) {
            Set<ComplaintForm> complaintFormSet = complaintTitle.get().getComplaintFormSet();

            for(ComplaintForm c : complaintFormSet) {
                c.setComplaintTitleSet(new HashSet<>());
                complaintFormRepository.save(c);
            }

            complaintTitleService.deleteById(id);
            redirectAttributes.addFlashAttribute("deleted_success", true);
        }
        else {
            redirectAttributes.addFlashAttribute("complaint_title_not_found", true);
        }

        return "redirect:/api/complaintTitle/";

    }

}
