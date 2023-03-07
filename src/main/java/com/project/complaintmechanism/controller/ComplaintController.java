package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.ComplaintForm;
import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintModel;
import com.project.complaintmechanism.model.DailyLimitModel;
import com.project.complaintmechanism.service.CompanyService;
import com.project.complaintmechanism.service.ComplaintFormService;
import com.project.complaintmechanism.service.ComplaintTitleService;
import com.project.complaintmechanism.service.DailyLimitService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    CompanyService companyService;
    @Autowired
    ComplaintTitleService complaintTitleService;
    @Autowired
    ComplaintFormService complaintFormService;
    @Autowired
    DailyLimitService dailyLimitService;

    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("dailyLimit", new DailyLimitModel());
        return getByPage(model, null, null, null, 1, 5);
    }

    @GetMapping("/page")
    public String getPaginated(Model model, @RequestParam(required = false) String companyName, @RequestParam(required = false) String complaintTitle, @RequestParam(required = false) String date, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        model.addAttribute("dailyLimit", new DailyLimitModel());
        return getByPage(model, companyName, complaintTitle, date, page, size);

    }

    public String getByPage(Model model, String companyName, String complaintTitle, String date, int page, int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        Page<ComplaintForm> complaintPage;
        boolean isEmptyCompanyName = (companyName == null || companyName.equalsIgnoreCase(""));
        boolean isEmptyComplaintTitle = (complaintTitle == null || complaintTitle.equalsIgnoreCase(""));
        boolean isEmptyDate = (date == null || date.equalsIgnoreCase(""));

//        if(isEmptyCompanyName && isEmptyComplaintTitle && isEmptyDate) {
            complaintPage = complaintFormService.findByPage(paging);
//        }
//        else if(!isEmptyCompanyName && isEmptyComplaintTitle && isEmptyDate) {
//            complaintPage = complaintFormService.findByPageWithCompanyName(companyName, paging);
//            model.addAttribute("companyName", companyName);
//        }
//        else if(isEmptyCompanyName && !isEmptyComplaintTitle && isEmptyDate) {
//            complaintPage = complaintFormService.findByPageWithComplaintTitle(complaintTitle, paging);
//            model.addAttribute("complaintTitle", complaintTitle);
//        }
//        else if(isEmptyCompanyName && isEmptyComplaintTitle && !isEmptyDate) {
//            complaintPage = complaintFormService.findByPageWithDate(date, paging);
//            model.addAttribute("date", date);
//        }
//        else if(!isEmptyCompanyName && !isEmptyComplaintTitle && isEmptyDate) {
//            complaintPage = complaintFormService.findByPageWithCompanyNameAndComplaintTitle(companyName, complaintTitle, paging);
//            model.addAttribute("companyName", companyName);
//            model.addAttribute("complaintTitle", complaintTitle);
//        }
//        else if(!isEmptyCompanyName && isEmptyComplaintTitle && !isEmptyDate) {
//            complaintPage = complaintFormService.findByPageWithCompanyNameAndDate(companyName, date, paging);
//            model.addAttribute("companyName", companyName);
//            model.addAttribute("date", date);
//        }
//        else if(isEmptyCompanyName && !isEmptyComplaintTitle && !isEmptyDate) {
//            complaintPage = complaintFormService.findByPageWithComplaintTitleAndDate(complaintTitle, date, paging);
//            model.addAttribute("complaintTitle", complaintTitle);
//            model.addAttribute("date", date);
//        }
//        else {
//            complaintPage = complaintFormService.findByPageWithCompanyNameAndComplaintTitleAndDate(companyName, complaintTitle, date, paging);
//            model.addAttribute("companyName", companyName);
//            model.addAttribute("complaintTitle", complaintTitle);
//            model.addAttribute("date", date);
//        }

        List<ComplaintForm> complaintFormList = complaintPage.getContent();
        List<ComplaintModel> complaintList = new ArrayList<>();

        for(ComplaintForm complaintForm : complaintFormList) {
            String description = complaintForm.getDescription();
            LocalDateTime submitted_at = complaintForm.getSubmittedAt();
            LocalDate submitted_date = submitted_at.toLocalDate();
            Set<ComplaintTitle> complaintTitleSet = complaintForm.getComplaintTitleSet();
            List<String> complaintTitleList = new ArrayList<>();
            for(ComplaintTitle ct : complaintTitleSet) {
                complaintTitleList.add(ct.getName());

            }

            ComplaintModel complaintModel = ComplaintModel.builder()
                                                          .id(complaintForm.getId())
                                                          .description(description)
                                                          .date(submitted_date)
                                                          .readStatus(complaintForm.isReadStatus() ? "Read" : "Unread")
                                                          .acceptedStatus(complaintForm.getAcceptedStatus() == -1 ? "Unassigned" : complaintForm.getAcceptedStatus() == 0 ? "Rejected" : "Accepted")
                                                          .solvedStatus(complaintForm.isSolvedStatus() ? "Solved" : "Unsolved")
                                                          .tempDeletedStatus(complaintForm.isTempDeletedStatus())
                                                          .name(complaintForm.getName())
                                                          .companyName(complaintForm.getCompany().getName())
                                                          .complaintTitle(String.join(", ", complaintTitleList))
                                                          .build();
            complaintList.add(complaintModel);
        }


        List<String> companyNameList = companyService.findAllNames();
        List<String> complaintTitleNameList = complaintTitleService.findAllNames();
        int maxLimit = dailyLimitService.findFirstByOrderById().getMaxLimit();
        model.addAttribute("companyNameList", companyNameList);
        model.addAttribute("complaintTitleNameList", complaintTitleNameList);
        model.addAttribute("complaintList", complaintList);
        model.addAttribute("currentPage", complaintPage.getNumber() + 1);
        model.addAttribute("totalItems", complaintPage.getTotalElements());
        model.addAttribute("totalPages", complaintPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("limit", maxLimit);
        model.addAttribute("complaintCount", complaintFormService.findComplaintFormCountToday());
        return "complaint_list";

    }

    @PostMapping("/dailyLimit/update")
    public String update(@Valid @ModelAttribute("dailyLimit") DailyLimitModel dailyLimitModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (!result.hasErrors()) {
            dailyLimitService.save(dailyLimitModel.getMaxLimit());
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/complaint/";
        }
        return getByPage(model, null, null, null, 1, 5);

    }

    @PostMapping("/changeReadStatus")
    @ResponseBody
    public void changeReadStatus(@RequestParam("id") int id, @RequestParam("status") boolean status) {
        complaintFormService.changeReadStatus(id, status);
    }

}
