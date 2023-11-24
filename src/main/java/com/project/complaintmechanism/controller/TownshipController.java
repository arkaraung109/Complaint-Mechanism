package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.TownshipModel;
import com.project.complaintmechanism.service.CityService;
import com.project.complaintmechanism.service.TownshipService;
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

@Controller
@RequestMapping("/api/township")
public class TownshipController {

    @Autowired
    private CityService cityService;
    @Autowired
    private TownshipService townshipService;

    @PostMapping("/cityId")
    @ResponseBody
    public List<Township> findByCityId(@RequestParam("cityId") long cityId) {
        return townshipService.findByCityId(cityId);
    }

    @GetMapping("/list")
    public String navigateToListPage(@RequestParam(defaultValue = "") String cityName, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, Model model, HttpSession httpSession) {
        Page<Township> townshipPage = townshipService.findByPage(cityName, keyword, pageNum, pageSize);
        List<Township> townshipList = townshipPage.getContent();
        List<String> cityNameList = cityService.findAllNames();

        httpSession.setAttribute("cityName", cityName);
        httpSession.setAttribute("keyword", keyword);
        httpSession.setAttribute("pageNum", pageNum);
        httpSession.setAttribute("pageSize", pageSize);

        model.addAttribute("cityNameList", cityNameList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("cityName", cityName);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", townshipPage.getNumber() + 1);
        model.addAttribute("totalItems", townshipPage.getTotalElements());
        model.addAttribute("totalPages", townshipPage.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "township/township_list";
    }

    @GetMapping("/add")
    public String navigateToAddPage(Model model) {
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("township", new TownshipModel());
        return "township/township_add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("township") TownshipModel townshipModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            townshipService.save(townshipModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/township/add";
        }

        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("township", townshipModel);
        return "township/township_add";
    }

    @GetMapping("/edit/{id}")
    public String navigateToEditPage(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        Township township = townshipService.findById(id);

        if (!Objects.isNull(township)) {
            List<City> cityList = cityService.findAll();
            TownshipModel townshipModel = TownshipModel.builder()
                    .id(township.getId())
                    .name(township.getName())
                    .cityId(township.getCity().getId())
                    .build();
            model.addAttribute("cityList", cityList);
            model.addAttribute("township", townshipModel);
            return "township/township_edit";
        } else {
            redirectAttributes.addFlashAttribute("township_not_found", true);
            return "redirect:/api/township/list";
        }
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("township") TownshipModel townshipModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            townshipService.update(townshipModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/township/list";
        }

        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("township", townshipModel);
        return "township/township_edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        Township township = townshipService.findById(id);

        if (!Objects.isNull(township)) {
            List<IndustrialZone> industrialZoneList = township.getIndustrialZoneList();
            if (industrialZoneList.isEmpty()) {
                townshipService.deleteById(id);
                redirectAttributes.addFlashAttribute("deleted_success", true);
            } else {
                redirectAttributes.addFlashAttribute("found_industrial_zone", true);
            }
        } else {
            redirectAttributes.addFlashAttribute("township_not_found", true);
        }
        return "redirect:/api/township/list";
    }

}
