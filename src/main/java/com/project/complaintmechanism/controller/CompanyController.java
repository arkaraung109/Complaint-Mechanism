package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.CompanyModel;
import com.project.complaintmechanism.service.CityService;
import com.project.complaintmechanism.service.CompanyService;
import com.project.complaintmechanism.service.IndustrialZoneService;
import com.project.complaintmechanism.service.TownshipService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CityService cityService;
    @Autowired
    private TownshipService townshipService;
    @Autowired
    private IndustrialZoneService industrialZoneService;
    @Autowired
    private CompanyService companyService;

    @PostMapping("/industrialZoneId")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<Company> findByIndustrialZoneId(@RequestParam("industrialZoneId") long industrialZoneId) {
        return companyService.findByIndustrialZoneId(industrialZoneId);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToListPage(@RequestParam(defaultValue = "") String cityName, @RequestParam(defaultValue = "") String townshipName, @RequestParam(defaultValue = "") String industrialZoneName, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, Model model, HttpSession httpSession) {
        Page<Company> companyPage = companyService.findByPage(cityName, townshipName, industrialZoneName, keyword, pageNum, pageSize);
        List<Company> companyList = companyPage.getContent();
        List<String> cityNameList = cityService.findAllNames();
        List<String> townshipNameList = townshipService.findAllNames();
        List<String> industrialZoneNameList = industrialZoneService.findAllNames();

        httpSession.setAttribute("cityName", cityName);
        httpSession.setAttribute("townshipName", townshipName);
        httpSession.setAttribute("industrialZoneName", industrialZoneName);
        httpSession.setAttribute("keyword", keyword);
        httpSession.setAttribute("pageNum", pageNum);
        httpSession.setAttribute("pageSize", pageSize);

        model.addAttribute("cityNameList", cityNameList);
        model.addAttribute("townshipNameList", townshipNameList);
        model.addAttribute("industrialZoneNameList", industrialZoneNameList);
        model.addAttribute("companyList", companyList);
        model.addAttribute("cityName", cityName);
        model.addAttribute("townshipName", townshipName);
        model.addAttribute("industrialZoneName", industrialZoneName);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", companyPage.getNumber() + 1);
        model.addAttribute("totalItems", companyPage.getTotalElements());
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "company/company_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToAddPage(Model model) {
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("company", new CompanyModel());
        return "company/company_add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@Valid @ModelAttribute("company") CompanyModel companyModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            companyService.save(companyModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/company/add";
        }

        List<City> cityList = cityService.findAll();
        List<Township> townshipList = townshipService.findByCityId(companyModel.getCityId());
        List<IndustrialZone> industrialZoneList = industrialZoneService.findByTownshipId(companyModel.getTownshipId());
        model.addAttribute("cityList", cityList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("industrialZoneList", industrialZoneList);
        model.addAttribute("company", companyModel);
        return "company/company_add";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToEditPage(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        Company company = companyService.findById(id);

        if (!Objects.isNull(company)) {
            List<City> cityList = cityService.findAll();
            List<Township> townshipList = townshipService.findByCityId(company.getIndustrialZone().getTownship().getCity().getId());
            List<IndustrialZone> industrialZoneList = industrialZoneService.findByTownshipId(company.getIndustrialZone().getTownship().getId());
            CompanyModel companyModel = CompanyModel.builder()
                    .id(company.getId())
                    .name(company.getName())
                    .activeStatus(company.isActiveStatus())
                    .cityId(company.getIndustrialZone().getTownship().getCity().getId())
                    .townshipId(company.getIndustrialZone().getTownship().getId())
                    .industrialZoneId(company.getIndustrialZone().getId())
                    .build();
            model.addAttribute("cityList", cityList);
            model.addAttribute("townshipList", townshipList);
            model.addAttribute("industrialZoneList", industrialZoneList);
            model.addAttribute("company", companyModel);
            return "company/company_edit";
        } else {
            redirectAttributes.addFlashAttribute("company_not_found", true);
            return "redirect:/api/company/list";
        }
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@Valid @ModelAttribute("company") CompanyModel companyModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            companyService.update(companyModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/company/list";
        }

        List<City> cityList = cityService.findAll();
        List<Township> townshipList = townshipService.findByCityId(companyModel.getCityId());
        List<IndustrialZone> industrialZoneList = industrialZoneService.findByTownshipId(companyModel.getTownshipId());
        model.addAttribute("cityList", cityList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("industrialZoneList", industrialZoneList);
        model.addAttribute("company", companyModel);
        return "company/company_edit";
    }

    @GetMapping("/status/{status}/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable("status") boolean status, @PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        Company company = companyService.findById(id);

        if(!Objects.isNull(company)) {
            companyService.changeStatus(status, id);
            if(status) {
                redirectAttributes.addFlashAttribute("active_status_success", true);
            }
            else {
                redirectAttributes.addFlashAttribute("inactive_status_success", true);
            }
        }
        else {
            redirectAttributes.addFlashAttribute("company_not_found", true);
        }
        return "redirect:/api/company/list";
    }

}
