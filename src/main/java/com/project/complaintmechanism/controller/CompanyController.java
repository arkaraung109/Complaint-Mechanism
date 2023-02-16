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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CityService cityService;
    @Autowired
    TownshipService townshipService;
    @Autowired
    IndustrialZoneService industrialZoneService;
    @Autowired
    CompanyService companyService;

    @GetMapping("/")
    public String showList(Model model) {

        model.addAttribute("company", new CompanyModel());
        return getByPage(model, null, null, null, null, 1, 5);

    }

    @GetMapping("/page")
    public String getPaginated(Model model, @RequestParam(required = false) String cityName, @RequestParam(required = false) String townshipName, @RequestParam(required = false) String industrialZoneName, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        model.addAttribute("company", new CompanyModel());
        return getByPage(model, cityName, townshipName, industrialZoneName, keyword, page, size);

    }

    public String getByPage(Model model, String cityName, String townshipName, String industrialZoneName, String keyword, int page, int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Company> companyPage;
        boolean isEmptyKeyword = (keyword == null || keyword.equalsIgnoreCase(""));
        boolean isEmptyCityName = (cityName == null || cityName.equalsIgnoreCase(""));
        boolean isEmptyTownshipName = (townshipName == null || townshipName.equalsIgnoreCase(""));
        boolean isEmptyIndustrialZoneName = (industrialZoneName == null || industrialZoneName.equalsIgnoreCase(""));

        if(isEmptyKeyword && isEmptyCityName && isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPage(paging);
        }
        else if(!isEmptyKeyword && isEmptyCityName && isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCompanyName(keyword, paging);
            model.addAttribute("keyword", keyword);
        }
        else if(isEmptyKeyword && !isEmptyCityName && isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityName(cityName, paging);
            model.addAttribute("cityName", cityName);
        }
        else if(isEmptyKeyword && isEmptyCityName && !isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithTownshipName(townshipName, paging);
            model.addAttribute("townshipName", townshipName);
        }
        else if(isEmptyKeyword && isEmptyCityName && isEmptyTownshipName && !isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithIndustrialZoneName(industrialZoneName, paging);
            model.addAttribute("industrialZoneName", industrialZoneName);
        }
        else if(!isEmptyKeyword && !isEmptyCityName && isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityNameAndCompanyName(cityName, keyword, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("keyword", keyword);
        }
        else if(!isEmptyKeyword && isEmptyCityName && !isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithTownshipNameAndCompanyName(townshipName, keyword, paging);
            model.addAttribute("townshipName", townshipName);
            model.addAttribute("keyword", keyword);
        }
        else if(!isEmptyKeyword && isEmptyCityName && isEmptyTownshipName && !isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithIndustrialZoneNameAndCompanyName(industrialZoneName, keyword, paging);
            model.addAttribute("industrialZoneName", industrialZoneName);
            model.addAttribute("keyword", keyword);
        }
        else if(isEmptyKeyword && !isEmptyCityName && !isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityNameAndTownshipName(cityName, townshipName, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("townshipName", townshipName);
        }
        else if(isEmptyKeyword && !isEmptyCityName && isEmptyTownshipName && !isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityNameAndIndustrialZoneName(cityName, industrialZoneName, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("industrialZoneName", industrialZoneName);
        }
        else if(isEmptyKeyword && isEmptyCityName && !isEmptyTownshipName && !isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithTownshipNameAndIndustrialZoneName(townshipName, industrialZoneName, paging);
            model.addAttribute("townshipName", townshipName);
            model.addAttribute("industrialZoneName", industrialZoneName);
        }
        else if(!isEmptyKeyword && !isEmptyCityName && !isEmptyTownshipName && isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityNameAndTownshipNameAndCompanyName(cityName, townshipName, keyword, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("townshipName", townshipName);
            model.addAttribute("keyword", keyword);
        }
        else if(isEmptyKeyword && !isEmptyCityName && !isEmptyTownshipName && !isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(cityName, townshipName, industrialZoneName, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("townshipName", townshipName);
        }
        else if(!isEmptyKeyword && isEmptyCityName && !isEmptyTownshipName && !isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(cityName, townshipName, industrialZoneName, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("townshipName", townshipName);
            model.addAttribute("industrialZoneName", industrialZoneName);
        }
        else if(!isEmptyKeyword && !isEmptyCityName && isEmptyTownshipName && !isEmptyIndustrialZoneName) {
            companyPage = companyService.findByPageWithCityNameAndIndustrialZoneNameAndCompanyName(cityName, industrialZoneName, keyword, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("industrialZoneName", industrialZoneName);
            model.addAttribute("keyword", keyword);
        }
        else {
            companyPage = companyService.findByPageWithCityNameAndTownshipNameAndIndustrialZoneNameAndCompanyName(cityName, townshipName, industrialZoneName, keyword, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("townshipName", townshipName);
            model.addAttribute("industrialZoneName", industrialZoneName);
            model.addAttribute("keyword", keyword);
        }

        List<Company> companyList = companyPage.getContent();
        List<City> cityList = cityService.findAll();
        List<Township> townshipList = townshipService.findAll();
        List<IndustrialZone> industrialZoneList = industrialZoneService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("industrialZoneList", industrialZoneList);
        model.addAttribute("companyList", companyList);
        model.addAttribute("currentPage", companyPage.getNumber() + 1);
        model.addAttribute("totalItems", companyPage.getTotalElements());
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "company_list";

    }

    @PostMapping("/cityToTownship")
    @ResponseBody
    public List<Township> findTownshipListByCityName(@RequestParam("cityName") String cityName) {

        return townshipService.findByCityName(cityName);

    }

    @PostMapping("/townshipToIndustrialZone")
    @ResponseBody
    public List<IndustrialZone> findIndustrialZoneListByTownshipName(@RequestParam("townshipName") String townshipName) {

        return industrialZoneService.findByTownshipName(townshipName);

    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("company") CompanyModel companyModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if(!result.hasErrors()) {
            companyService.saveOrUpdate(companyModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/company/";
        }

        model.addAttribute("township_select", companyModel.getTownshipName());
        model.addAttribute("industrial_zone_select", companyModel.getIndustrialZoneName());
        return getByPage(model, null, null, null, null, 1, 5);

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("company") CompanyModel companyModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        Optional<Company> company = companyService.findById(id);

        if(company.isPresent()) {
            if(!result.hasErrors()) {
                companyModel.setId(id);
                companyService.saveOrUpdate(companyModel);
                redirectAttributes.addFlashAttribute("updated_success", true);
                return "redirect:/api/company/";
            }

            model.addAttribute("township_select", companyModel.getTownshipName());
            model.addAttribute("industrial_zone_select", companyModel.getIndustrialZoneName());
            return getByPage(model, null, null, null, null, 1, 5);
        }
        else {
            redirectAttributes.addFlashAttribute("company_not_found", true);
            return "redirect:/api/company/";
        }

    }

    @GetMapping("/status/{status}/{id}")
    public String changeStatus(@PathVariable("status") boolean status, @PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        Optional<Company> company = companyService.findById(id);

        if(company.isPresent()) {
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

        return "redirect:/api/company/";

    }

}
