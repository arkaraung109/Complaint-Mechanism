package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.Complaint;
import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintTitleModel;
import com.project.complaintmechanism.service.ComplaintTitleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/api/complaintTitle")
public class ComplaintTitleController {

    @Autowired
    private ComplaintTitleService complaintTitleService;

    @GetMapping("/list")
    public String navigateToListPage(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, Model model, HttpSession httpSession) {
        Page<ComplaintTitle> complaintTitlePage = complaintTitleService.findByPage(keyword, pageNum, pageSize);
        List<ComplaintTitle> complaintTitleList = complaintTitlePage.getContent();

        httpSession.setAttribute("keyword", keyword);
        httpSession.setAttribute("pageNum", pageNum);
        httpSession.setAttribute("pageSize", pageSize);

        model.addAttribute("complaintTitleList", complaintTitleList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", complaintTitlePage.getNumber() + 1);
        model.addAttribute("totalItems", complaintTitlePage.getTotalElements());
        model.addAttribute("totalPages", complaintTitlePage.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "complaint_title/complaint_title_list";
    }

    @GetMapping("/add")
    public String navigateToAddPage(Model model) {
        model.addAttribute("complaintTitle", new ComplaintTitleModel());
        return "complaint_title/complaint_title_add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("complaintTitle") ComplaintTitleModel complaintTitleModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            complaintTitleService.save(complaintTitleModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/complaintTitle/add";
        }
        model.addAttribute("complaintTitle", complaintTitleModel);
        return "complaint_title/complaint_title_add";
    }

    @GetMapping("/edit/{id}")
    public String navigateToEditPage(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        ComplaintTitle complaintTitle = complaintTitleService.findById(id);

        if (!Objects.isNull(complaintTitle)) {
            ComplaintTitleModel complaintTitleModel = ComplaintTitleModel.builder()
                    .id(complaintTitle.getId())
                    .name(complaintTitle.getName())
                    .build();
            model.addAttribute("complaintTitle", complaintTitleModel);
            return "complaint_title/complaint_title_edit";
        } else {
            redirectAttributes.addFlashAttribute("complaint_title_not_found", true);
            return "redirect:/api/complaintTitle/list";
        }
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("complaintTitle") ComplaintTitleModel complaintTitleModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            complaintTitleService.update(complaintTitleModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/complaintTitle/list";
        }

        model.addAttribute("complaintTitle", complaintTitleModel);
        return "complaint_title/complaint_title_edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        ComplaintTitle complaintTitle = complaintTitleService.findById(id);

        if (!Objects.isNull(complaintTitle)) {
            Set<Complaint> complaintSet = complaintTitle.getComplaintSet();
            if (complaintSet.isEmpty()) {
                complaintTitleService.deleteById(id);
                redirectAttributes.addFlashAttribute("deleted_success", true);
            } else {
                redirectAttributes.addFlashAttribute("found_complaint_form", true);
            }
        } else {
            redirectAttributes.addFlashAttribute("complaint_title_not_found", true);
        }
        return "redirect:/api/complaintTitle/list";
    }

}
