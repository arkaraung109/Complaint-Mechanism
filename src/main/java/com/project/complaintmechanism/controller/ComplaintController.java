package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.ComplaintForm;
import com.project.complaintmechanism.entity.ComplaintTitle;
import com.project.complaintmechanism.model.ComplaintModel;
import com.project.complaintmechanism.model.DailyLimitModel;
import com.project.complaintmechanism.service.CompanyService;
import com.project.complaintmechanism.service.ComplaintFormService;
import com.project.complaintmechanism.service.ComplaintTitleService;
import com.project.complaintmechanism.service.DailyLimitService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/all")
    public String showAllList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "All");
        return showList(model, httpSession);

    }

    @GetMapping("/trash")
    public String showTrashList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Trash");
        return showList(model, httpSession);

    }

    @GetMapping("/read")
    public String showReadList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Read");
        return showList(model, httpSession);

    }

    @GetMapping("/unread")
    public String showUnreadList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Unread");
        return showList(model, httpSession);

    }

    @GetMapping("/accepted")
    public String showAcceptedList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Accepted");
        return showList(model, httpSession);

    }

    @GetMapping("/rejected")
    public String showRejectedList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Rejected");
        return showList(model, httpSession);

    }

    @GetMapping("/pending")
    public String showPendingList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Pending");
        return showList(model, httpSession);

    }

    @GetMapping("/solved")
    public String showSolvedList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Solved");
        return showList(model, httpSession);

    }

    @GetMapping("/unsolved")
    public String showUnsolvedList(Model model, HttpSession httpSession) {

        httpSession.setAttribute("status", "Unsolved");
        return showList(model, httpSession);

    }

    public String showList(Model model, HttpSession httpSession) {

        model.addAttribute("dailyLimit", new DailyLimitModel());
        return getByPage(model, httpSession, null, null, null, 1, 5);

    }

    @GetMapping("/page")
    public String getPaginated(Model model, HttpSession httpSession, @RequestParam(required = false) String companyName, @RequestParam(required = false) String complaintTitleName, @RequestParam(required = false) String date, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        model.addAttribute("dailyLimit", new DailyLimitModel());
        return getByPage(model, httpSession, companyName, complaintTitleName, date, page, size);

    }

    public String getByPage(Model model, HttpSession httpSession, String companyName, String complaintTitleName, String date, int page, int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        Page<ComplaintForm> complaintPage = null;
        boolean isEmptyCompanyName = (companyName == null || companyName.equalsIgnoreCase(""));
        boolean isEmptyComplaintTitleName = (complaintTitleName == null || complaintTitleName.equalsIgnoreCase(""));
        boolean isEmptyDate = (date == null || date.equalsIgnoreCase(""));
        String status = (String) httpSession.getAttribute("status");

        if(isEmptyCompanyName && isEmptyComplaintTitleName && isEmptyDate) {
            complaintPage = complaintFormService.findByPage(status, paging);
        }
        else if(!isEmptyCompanyName && isEmptyComplaintTitleName && isEmptyDate) {
            complaintPage = complaintFormService.findByPageWithCompanyName(status, companyName, paging);
            model.addAttribute("companyName", companyName);
        }
        else if(isEmptyCompanyName && !isEmptyComplaintTitleName && isEmptyDate) {
            complaintPage = complaintFormService.findByPageWithComplaintTitleName(status, complaintTitleName, paging);
            model.addAttribute("complaintTitleName", complaintTitleName);
        }
        else if(isEmptyCompanyName && isEmptyComplaintTitleName && !isEmptyDate) {
            complaintPage = complaintFormService.findByPageWithDate(status, date, paging);
            model.addAttribute("date", date);
        }
        else if(!isEmptyCompanyName && !isEmptyComplaintTitleName && isEmptyDate) {
            complaintPage = complaintFormService.findByPageWithCompanyNameAndComplaintTitleName(status, companyName, complaintTitleName, paging);
            model.addAttribute("companyName", companyName);
            model.addAttribute("complaintTitleName", complaintTitleName);
        }
        else if(!isEmptyCompanyName && isEmptyComplaintTitleName && !isEmptyDate) {
            complaintPage = complaintFormService.findByPageWithCompanyNameAndDate(status, companyName, date, paging);
            model.addAttribute("companyName", companyName);
            model.addAttribute("date", date);
        }
        else if(isEmptyCompanyName && !isEmptyComplaintTitleName && !isEmptyDate) {
            complaintPage = complaintFormService.findByPageWithComplaintTitleNameAndDate(status, complaintTitleName, date, paging);
            model.addAttribute("complaintTitleName", complaintTitleName);
            model.addAttribute("date", date);
        }
        else {
            complaintPage = complaintFormService.findByPageWithCompanyNameAndComplaintTitleNameAndDate(status, companyName, complaintTitleName, date, paging);
            model.addAttribute("companyName", companyName);
            model.addAttribute("complaintTitleName", complaintTitleName);
            model.addAttribute("date", date);
        }

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
                                                          .acceptedStatus(complaintForm.getAcceptedStatus() == -1 ? "Pending" : complaintForm.getAcceptedStatus() == 0 ? "Rejected" : "Accepted")
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
    public String update(@Valid @ModelAttribute("dailyLimit") DailyLimitModel dailyLimitModel, HttpSession httpSession, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (!result.hasErrors()) {
            dailyLimitService.save(dailyLimitModel.getMaxLimit());
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/complaint/";
        }
        return getByPage(model,httpSession, null, null, null, 1, 5);

    }

    @PostMapping("/changeReadStatus")
    @ResponseBody
    public void changeReadStatus(@RequestParam("id") int id, @RequestParam("status") boolean status) {

        complaintFormService.changeReadStatus(id, status);

    }

    @PostMapping("/changeTempDeletedStatus")
    @ResponseBody
    public void changeTempDeletedStatus(@RequestParam("id") int id, @RequestParam("status") boolean status) {

        complaintFormService.changeTempDeletedStatus(id, status);

    }

    @PostMapping("/emptyTrash")
    @ResponseBody
    public void emptyTrash() {

        complaintFormService.emptyTrash();

    }

}
