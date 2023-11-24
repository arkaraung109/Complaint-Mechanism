package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.model.DailyLimitModel;
import com.project.complaintmechanism.service.ComplaintService;
import com.project.complaintmechanism.service.DailyLimitService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/dailyLimit")
public class DailyLimitController {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private DailyLimitService dailyLimitService;

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("dailyLimit") DailyLimitModel dailyLimitModel, BindingResult result, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Object object = httpSession.getAttribute("complaintStatus");
        String complaintStatus;
        if(object == null) {
            httpSession.setAttribute("complaintStatus", "All");
            complaintStatus = "All";
        } else {
            complaintStatus = (String) object;
        }

        if (!result.hasErrors()) {
            dailyLimitService.update(dailyLimitModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/complaint/list/" + complaintStatus.toLowerCase();
        }
        redirectAttributes.addFlashAttribute("daily_limit_error", true);
        return "redirect:/api/complaint/list/" + complaintStatus.toLowerCase();
    }

}
