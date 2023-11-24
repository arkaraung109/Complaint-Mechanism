package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.entity.Township;
import com.project.complaintmechanism.model.CityModel;
import com.project.complaintmechanism.service.CityService;
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
@RequestMapping("/api/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/list")
    public String navigateToListPage(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, HttpSession httpSession) {
        Page<City> cityPage = cityService.findByPage(keyword, pageNum, pageSize);
        List<City> cityList = cityPage.getContent();

        httpSession.setAttribute("keyword", keyword);
        httpSession.setAttribute("pageNum", pageNum);
        httpSession.setAttribute("pageSize", pageSize);

        model.addAttribute("cityList", cityList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", cityPage.getNumber() + 1);
        model.addAttribute("totalItems", cityPage.getTotalElements());
        model.addAttribute("totalPages", cityPage.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "city/city_list";
    }

    @GetMapping("/add")
    public String navigateToAddPage(Model model) {
        model.addAttribute("city", new CityModel());
        return "city/city_add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("city") CityModel cityModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            cityService.save(cityModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/city/add";
        }
        model.addAttribute("city", cityModel);
        return "city/city_add";
    }

    @GetMapping("/edit/{id}")
    public String navigateToEditPage(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        City city = cityService.findById(id);

        if (!Objects.isNull(city)) {
            CityModel cityModel = CityModel.builder()
                    .id(city.getId())
                    .name(city.getName())
                    .build();
            model.addAttribute("city", cityModel);
            return "city/city_edit";
        } else {
            redirectAttributes.addFlashAttribute("city_not_found", true);
            return "redirect:/api/city/list";
        }
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("city") CityModel cityModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            cityService.update(cityModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/city/list";
        }

        model.addAttribute("city", cityModel);
        return "city/city_edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        City city = cityService.findById(id);

        if (!Objects.isNull(city)) {
            List<Township> townshipList = city.getTownshipList();
            if (townshipList.isEmpty()) {
                cityService.deleteById(id);
                redirectAttributes.addFlashAttribute("deleted_success", true);
            } else {
                redirectAttributes.addFlashAttribute("found_township", true);
            }
        } else {
            redirectAttributes.addFlashAttribute("city_not_found", true);
        }
        return "redirect:/api/city/list";
    }

}
