package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.IndustrialZoneModel;
import com.project.complaintmechanism.service.CityService;
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
@RequestMapping("/api/industrialZone")
public class IndustrialZoneController {

    @Autowired
    CityService cityService;
    @Autowired
    TownshipService townshipService;
    @Autowired
    IndustrialZoneService industrialZoneService;

    @GetMapping("/")
    public String showList(Model model) {

        model.addAttribute("industrialZone", new IndustrialZoneModel());
        return getByPage(model, null, null, null, 1, 5);

    }

    @GetMapping("/page")
    public String getPaginated(Model model, @RequestParam(required = false) String cityName, @RequestParam(required = false) String townshipName, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        model.addAttribute("industrialZone", new IndustrialZoneModel());
        return getByPage(model, cityName, townshipName, keyword, page, size);

    }

    public String getByPage(Model model, String cityName, String townshipName, String keyword, int page, int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        Page<IndustrialZone> industrialZonePage;
        boolean isEmptyKeyword = (keyword == null || keyword.equalsIgnoreCase(""));
        boolean isEmptyCityName = (cityName == null || cityName.equalsIgnoreCase(""));
        boolean isEmptyTownshipName = (townshipName == null || townshipName.equalsIgnoreCase(""));

        if(isEmptyKeyword && isEmptyCityName && isEmptyTownshipName) {
            industrialZonePage = industrialZoneService.findByPage(paging);
        }
        else if(!isEmptyKeyword && isEmptyCityName && isEmptyTownshipName) {
            industrialZonePage = industrialZoneService.findByPageWithIndustrialZoneName(keyword, paging);
            model.addAttribute("keyword", keyword);
        }
        else if(isEmptyKeyword && !isEmptyCityName && isEmptyTownshipName) {
            industrialZonePage = industrialZoneService.findByPageWithCityName(cityName, paging);
            model.addAttribute("cityName", cityName);
        }
        else if(isEmptyKeyword && isEmptyCityName && !isEmptyTownshipName) {
            industrialZonePage = industrialZoneService.findByPageWithTownshipName(townshipName, paging);
            model.addAttribute("townshipName", townshipName);
        }
        else if(!isEmptyKeyword && !isEmptyCityName && isEmptyTownshipName) {
            industrialZonePage = industrialZoneService.findByPageWithCityNameAndIndustrialZoneName(cityName, keyword, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("keyword", keyword);
        }
        else if(!isEmptyKeyword && isEmptyCityName && !isEmptyTownshipName) {
            industrialZonePage = industrialZoneService.findByPageWithTownshipNameAndIndustrialZoneName(townshipName, keyword, paging);
            model.addAttribute("townshipName", townshipName);
            model.addAttribute("keyword", keyword);
        }
        else if(isEmptyKeyword && !isEmptyCityName && !isEmptyTownshipName) {
            industrialZonePage = industrialZoneService.findbyPageWithCityNameAndTownshipName(cityName, townshipName, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("townshipName", townshipName);
        }
        else {
            industrialZonePage = industrialZoneService.findByPageWithCityNameAndTownshipNameAndIndustrialZoneName(cityName, townshipName, keyword, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("townshipName", townshipName);
            model.addAttribute("keyword", keyword);
        }

        List<IndustrialZone> industrialZoneList = industrialZonePage.getContent();
        List<City> cityList = cityService.findAll();
        List<Township> townshipList = townshipService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("industrialZoneList", industrialZoneList);
        model.addAttribute("currentPage", industrialZonePage.getNumber() + 1);
        model.addAttribute("totalItems", industrialZonePage.getTotalElements());
        model.addAttribute("totalPages", industrialZonePage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "industrial_zone_list";

    }

    @PostMapping("/cityToTownship")
    @ResponseBody
    public List<Township> findTownshipListByCityName(@RequestParam("cityName") String cityName) {

        return townshipService.findByCityName(cityName);

    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("industrialZone") IndustrialZoneModel industrialZoneModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if(!result.hasErrors()) {
            industrialZoneService.saveOrUpdate(industrialZoneModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/industrialZone/";
        }

        model.addAttribute("township_select", industrialZoneModel.getTownshipName());
        return getByPage(model, null, null, null, 1, 5);

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("industrialZone") IndustrialZoneModel industrialZoneModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        Optional<IndustrialZone> industrialZone = industrialZoneService.findById(id);

        if(industrialZone.isPresent()) {
            if(!result.hasErrors()) {
                industrialZoneModel.setId(id);
                industrialZoneService.saveOrUpdate(industrialZoneModel);
                redirectAttributes.addFlashAttribute("updated_success", true);
                return "redirect:/api/industrialZone/";
            }

            model.addAttribute("township_select", industrialZoneModel.getTownshipName());
            return getByPage(model, null, null, null, 1, 5);
        }
        else {
            redirectAttributes.addFlashAttribute("industrial_zone_not_found", true);
            return "redirect:/api/industrialZone/";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        Optional<IndustrialZone> industrialZone = industrialZoneService.findById(id);

        if(industrialZone.isPresent()) {
            List<Company> companyList = industrialZone.get().getCompanyList();

            if(companyList.isEmpty()) {
                industrialZoneService.deleteById(id);
                redirectAttributes.addFlashAttribute("deleted_success", true);
            }
            else {
                redirectAttributes.addFlashAttribute("deleted_fail", true);
            }
        }
        else {
            redirectAttributes.addFlashAttribute("industrial_zone_not_found", true);
        }

        return "redirect:/api/industrialZone/";

    }

}
