package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.City;
import com.project.complaintmechanism.model.CityModel;
import com.project.complaintmechanism.service.CityService;
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

@Controller
@RequestMapping("/api/city")
public class CityController {
    @Autowired
    CityService cityService;

    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("city", new CityModel());
        return getByPage(model, null, 1, 5);
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("city") CityModel cityModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            cityService.save(cityModel);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/city/";
        }
        return getByPage(model, null, 1, 5);
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("city") CityModel cityModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(!result.hasErrors())  {
            cityModel.setId(id);
            cityService.update(cityModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/api/city/";
        }
        return getByPage(model, null, 1, 5);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        cityService.deleteById(id);
        redirectAttributes.addFlashAttribute("deleted_success", true);
        return "redirect:/api/city/";
    }

    @GetMapping("/page")
    public String getPaginated(Model model, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        model.addAttribute("city", new CityModel());
        return getByPage(model, keyword, page, size);
    }

    public String getByPage(Model model, String keyword, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<City> cityPage;

        if(keyword == null || keyword.equalsIgnoreCase("")) {
            cityPage = cityService.findByPage(paging);
        }
        else {
            cityPage = cityService.findByPageWithCityName(keyword, paging);
            model.addAttribute("keyword", keyword);
        }

        List<City> cityList = cityPage.getContent();
        model.addAttribute("cityList", cityList);
        model.addAttribute("currentPage", cityPage.getNumber() + 1);
        model.addAttribute("totalItems", cityPage.getTotalElements());
        model.addAttribute("totalPages", cityPage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "city_list";
    }

}
