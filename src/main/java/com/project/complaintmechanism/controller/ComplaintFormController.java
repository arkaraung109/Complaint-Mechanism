package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.ComplaintFormModel;
import com.project.complaintmechanism.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/api/complaintForm")
public class ComplaintFormController {

    @Autowired
    CityService cityService;
    @Autowired
    TownshipService townshipService;
    @Autowired
    IndustrialZoneService industrialZoneService;
    @Autowired
    CompanyService companyService;
    @Autowired
    ComplaintTitleService complaintTitleService;
    @Autowired
    ComplaintFormService complaintFormService;
    @Autowired
    DailyLimitService dailyLimitService;

    @GetMapping("/")
    public String displayForm(Model model, RedirectAttributes redirectAttributes) {

        int dailyLimit = dailyLimitService.findFirstByOrderById().getMaxLimit();
        boolean exceed = complaintFormService.checkLimitExceeded(dailyLimit);

        if(exceed) {
            redirectAttributes.addFlashAttribute("exceeded", true);
            return "redirect:/api/complaintForm/message";
        }

        model.addAttribute("complaintForm", new ComplaintFormModel());
        return showForm(model);

    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("complaintForm") ComplaintFormModel complaintFormModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws IOException {

        if (!result.hasErrors()) {
            int dailyLimit = dailyLimitService.findFirstByOrderById().getMaxLimit();
            boolean exceed = complaintFormService.checkLimitExceeded(dailyLimit);

            if(!exceed) {
                complaintFormService.save(complaintFormModel);
                redirectAttributes.addFlashAttribute("added_success", true);
            }
            else {
                redirectAttributes.addFlashAttribute("exceeded", true);
            }

            return "redirect:/api/complaintForm/message";
        }

        model.addAttribute("township_select", complaintFormModel.getTownshipName());
        model.addAttribute("industrial_zone_select", complaintFormModel.getIndustrialZoneName());
        model.addAttribute("company_select", complaintFormModel.getCompanyName());
        return showForm(model);

    }

    public String showForm(Model model) {

        List<String> cityNameList = cityService.findAllNames();
        List<String> complaintTitleNameList = complaintTitleService.findAllNames();
        model.addAttribute("cityNameList", cityNameList);
        model.addAttribute("complaintTitleNameList", complaintTitleNameList);
        return "complaint_form";

    }

    @PostMapping("/cityToTownship")
    @ResponseBody
    public List<Township> findTownshipListByCityName(@RequestParam("cityName") String cityName) {

        return townshipService.findByCityName(cityName);

    }

    @PostMapping("/townshipToIndustrialZone")
    @ResponseBody
    public List<IndustrialZone> findIndustrialZoneListByCityNameAndTownshipName(@RequestParam("cityName") String cityName, @RequestParam("townshipName") String townshipName) {

        return industrialZoneService.findByCityNameAndTownshipName(cityName, townshipName);

    }

    @PostMapping("/industrialZoneToCompany")
    @ResponseBody
    public List<Company> findCompanyListByCityNameAndTownshipNameAndIndustrialZoneName(@RequestParam("cityName") String cityName, @RequestParam("townshipName") String townshipName, @RequestParam("industrialZoneName") String industrialZoneName) {

        return companyService.findByCityNameAndTownshipNameAndIndustrialZoneName(cityName, townshipName, industrialZoneName);

    }

    @GetMapping("/message")
    public String showMessage() {
        return "message";
    }

}
