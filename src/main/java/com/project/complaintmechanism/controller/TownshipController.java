package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.TownshipModel;
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
@RequestMapping("/api/township")
public class TownshipController {

    @Autowired
    CityService cityService;
    @Autowired
    TownshipService townshipService;
    @Autowired
    IndustrialZoneService industrialZoneService;

    @GetMapping("/")
    public String showList(Model model) {

        model.addAttribute("township", new TownshipModel());
        return getByPage(model, null, null, 1, 5);

    }

    @GetMapping("/page")
    public String getPaginated(Model model, @RequestParam(required = false) String cityName, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        model.addAttribute("township", new TownshipModel());
        return getByPage(model, cityName, keyword, page, size);

    }

    public String getByPage(Model model, String cityName, String keyword, int page, int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Township> townshipPage;
        boolean isEmptyKeyword = (keyword == null || keyword.equalsIgnoreCase(""));
        boolean isEmptyCityName = (cityName == null || cityName.equalsIgnoreCase(""));

        if(isEmptyKeyword && isEmptyCityName) {
            townshipPage = townshipService.findByPage(paging);
        }
        else if(!isEmptyKeyword && isEmptyCityName) {
            townshipPage = townshipService.findByPageWithTownshipName(keyword, paging);
            model.addAttribute("keyword", keyword);
        }
        else if(isEmptyKeyword && !isEmptyCityName) {
            townshipPage = townshipService.findByPageWithCityName(cityName, paging);
            model.addAttribute("cityName", cityName);
        }
        else {
            townshipPage = townshipService.findByPageWithCityNameAndTownshipName(cityName, keyword, paging);
            model.addAttribute("cityName", cityName);
            model.addAttribute("keyword", keyword);
        }

        List<Township> townshipList = townshipPage.getContent();
        List<City> cityList = cityService.findAll();
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("cityList", cityList);
        model.addAttribute("currentPage", townshipPage.getNumber() + 1);
        model.addAttribute("totalItems", townshipPage.getTotalElements());
        model.addAttribute("totalPages", townshipPage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "township_list";

    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("township") TownshipModel townshipModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if(!result.hasErrors()) {
            townshipService.saveOrUpdate(townshipModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/township/";
        }
        return getByPage(model, null, null, 1, 5);

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("township") TownshipModel townshipModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        Optional<Township> township = townshipService.findById(id);
        if(township.isPresent()) {
            if(!result.hasErrors()) {
                townshipModel.setId(id);
                townshipService.saveOrUpdate(townshipModel);
                redirectAttributes.addFlashAttribute("updated_success", true);
                return "redirect:/api/township/";
            }

            return getByPage(model, null, null, 1, 5);
        }
        else {
            redirectAttributes.addFlashAttribute("township_not_found", true);
            return "redirect:/api/township/";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        Optional<Township> township = townshipService.findById(id);

        if(township.isPresent()) {
            List<IndustrialZone> industrialZoneList = township.get().getIndustrialZoneList();

            if(industrialZoneList.isEmpty()) {
                townshipService.deleteById(id);
                redirectAttributes.addFlashAttribute("deleted_success", true);
            }
            else {
                redirectAttributes.addFlashAttribute("deleted_fail", true);
            }
        }
        else {
            redirectAttributes.addFlashAttribute("township_not_found", true);
        }

        return "redirect:/api/township/";

    }

}
