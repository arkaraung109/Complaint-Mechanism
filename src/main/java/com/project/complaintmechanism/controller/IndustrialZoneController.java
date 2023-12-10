package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.Company;
import com.project.complaintmechanism.entity.IndustrialZone;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.IndustrialZoneModel;
import com.project.complaintmechanism.service.CityService;
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
@RequestMapping("/api/industrialZone")
public class IndustrialZoneController {

    @Autowired
    private CityService cityService;
    @Autowired
    private TownshipService townshipService;
    @Autowired
    private IndustrialZoneService industrialZoneService;

    @PostMapping("/townshipId")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<IndustrialZone> findByTownshipId(@RequestParam("townshipId") long townshipId) {
        return industrialZoneService.findByTownshipId(townshipId);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToListPage(@RequestParam(defaultValue = "") String cityName, @RequestParam(defaultValue = "") String townshipName, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, Model model, HttpSession httpSession) {
        Page<IndustrialZone> industrialZonePage = industrialZoneService.findByPage(cityName, townshipName, keyword, pageNum, pageSize);
        List<IndustrialZone> industrialZoneList = industrialZonePage.getContent();
        List<String> cityNameList = cityService.findAllNames();
        List<String> townshipNameList = townshipService.findAllNames();

        httpSession.setAttribute("cityName", cityName);
        httpSession.setAttribute("townshipName", townshipName);
        httpSession.setAttribute("keyword", keyword);
        httpSession.setAttribute("pageNum", pageNum);
        httpSession.setAttribute("pageSize", pageSize);

        model.addAttribute("cityNameList", cityNameList);
        model.addAttribute("townshipNameList", townshipNameList);
        model.addAttribute("industrialZoneList", industrialZoneList);
        model.addAttribute("cityName", cityName);
        model.addAttribute("townshipName", townshipName);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", industrialZonePage.getNumber() + 1);
        model.addAttribute("totalItems", industrialZonePage.getTotalElements());
        model.addAttribute("totalPages", industrialZonePage.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "industrial_zone/industrial_zone_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToAddPage(Model model) {
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("industrialZone", new IndustrialZoneModel());
        return "industrial_zone/industrial_zone_add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@Valid @ModelAttribute("industrialZone") IndustrialZoneModel industrialZoneModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            industrialZoneService.save(industrialZoneModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/industrialZone/add";
        }

        List<City> cityList = cityService.findAll();
        List<Township> townshipList = townshipService.findByCityId(industrialZoneModel.getCityId());
        model.addAttribute("cityList", cityList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("industrialZone", industrialZoneModel);
        return "industrial_zone/industrial_zone_add";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToEditPage(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        IndustrialZone industrialZone = industrialZoneService.findById(id);

        if (!Objects.isNull(industrialZone)) {
            List<City> cityList = cityService.findAll();
            List<Township> townshipList = townshipService.findByCityId(industrialZone.getTownship().getCity().getId());
            IndustrialZoneModel industrialZoneModel = IndustrialZoneModel.builder()
                    .id(industrialZone.getId())
                    .name(industrialZone.getName())
                    .cityId(industrialZone.getTownship().getCity().getId())
                    .townshipId(industrialZone.getTownship().getId())
                    .build();
            model.addAttribute("cityList", cityList);
            model.addAttribute("townshipList", townshipList);
            model.addAttribute("industrialZone", industrialZoneModel);
            return "industrial_zone/industrial_zone_edit";
        } else {
            redirectAttributes.addFlashAttribute("industrial_zone_not_found", true);
            return "redirect:/api/industrialZone/list";
        }
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@Valid @ModelAttribute("industrialZone") IndustrialZoneModel industrialZoneModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            industrialZoneService.update(industrialZoneModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/industrialZone/list";
        }

        List<City> cityList = cityService.findAll();
        List<Township> townshipList = townshipService.findByCityId(industrialZoneModel.getCityId());
        model.addAttribute("cityList", cityList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("industrialZone", industrialZoneModel);
        return "industrial_zone/industrial_zone_edit";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        IndustrialZone industrialZone = industrialZoneService.findById(id);

        if (!Objects.isNull(industrialZone)) {
            List<Company> companyList = industrialZone.getCompanyList();
            if (companyList.isEmpty()) {
                industrialZoneService.deleteById(id);
                redirectAttributes.addFlashAttribute("deleted_success", true);
            } else {
                redirectAttributes.addFlashAttribute("found_company", true);
            }
        } else {
            redirectAttributes.addFlashAttribute("industrial_zone_not_found", true);
        }
        return "redirect:/api/industrialZone/list";
    }

}
