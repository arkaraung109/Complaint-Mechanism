package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.service.CompanyService;
import com.project.complaintmechanism.service.ComplaintService;
import com.project.complaintmechanism.service.ComplaintTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComplaintTitleService complaintTitleService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping({"/dashboard"})
    public String navigateToDashboardPage(Model model) {
        int companyCount = companyService.findCount();
        int complaintTitleCount = complaintTitleService.findCount();
        int allComplaintCount = complaintService.findCountByAll();
        int readComplaintCount = complaintService.findCountByReadStatus(true);
        int unreadComplaintCount = complaintService.findCountByReadStatus(false);
        int acceptedComplaintCount = complaintService.findCountByAcceptedStatus(1);
        int rejectedComplaintCount = complaintService.findCountByAcceptedStatus(0);
        int pendingComplaintCount = complaintService.findCountByAcceptedStatus(-1);
        int solvedComplaintCount = complaintService.findCountBySolvedStatus(true);
        int unsolvedComplaintCount = complaintService.findCountBySolvedStatus(false);
        model.addAttribute("companyCount", companyCount);
        model.addAttribute("complaintTitleCount", complaintTitleCount);
        model.addAttribute("allComplaintCount", allComplaintCount);
        model.addAttribute("readComplaintCount", readComplaintCount);
        model.addAttribute("unreadComplaintCount", unreadComplaintCount);
        model.addAttribute("acceptedComplaintCount", acceptedComplaintCount);
        model.addAttribute("rejectedComplaintCount", rejectedComplaintCount);
        model.addAttribute("pendingComplaintCount", pendingComplaintCount);
        model.addAttribute("solvedComplaintCount", solvedComplaintCount);
        model.addAttribute("unsolvedComplaintCount", unsolvedComplaintCount);
        return "dashboard";
    }

    @GetMapping("/login")
    public String navigateToLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/403")
    public String navigateTo403Page() {
        return "403";
    }

}
