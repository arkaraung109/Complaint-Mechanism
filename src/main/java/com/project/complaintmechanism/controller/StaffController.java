package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.model.StaffModel;
import com.project.complaintmechanism.repository.UserRepository;
import com.project.complaintmechanism.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private UserService userService;

    @Value("${base.url}")
    private String baseURL;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToListPage(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, HttpSession httpSession) {
        Page<User> userPage = userService.findByPage(keyword, pageNum, pageSize);
        List<User> userList = userPage.getContent();

        httpSession.setAttribute("keyword", keyword);
        httpSession.setAttribute("pageNum", pageNum);
        httpSession.setAttribute("pageSize", pageSize);

        model.addAttribute("staffList", userList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", userPage.getNumber() + 1);
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "staff/staff_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String navigateToAddPage(Model model) {
        model.addAttribute("staff", new StaffModel());
        return "staff/staff_add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@Valid @ModelAttribute("staff") StaffModel staffModel, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        if (!result.hasErrors()) {
            User user = userService.save(staffModel);
            StringBuffer requestURL = request.getRequestURL();
            String servletPath = request.getServletPath();
            String url = requestURL.substring(0, requestURL.length() - servletPath.length()).toString();
            userService.sendVerificationEmail(user, url);
            redirectAttributes.addFlashAttribute("added_success", true);
            return "redirect:/api/staff/add";
        }
        model.addAttribute("staff", staffModel);
        return "staff/staff_add";
    }

    @GetMapping("/status/{status}/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable("status") boolean status, @PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);

        if(!Objects.isNull(user)) {
            userService.changeStatus(status, id);
            if(status) {
                redirectAttributes.addFlashAttribute("active_status_success", true);
            }
            else {
                redirectAttributes.addFlashAttribute("inactive_status_success", true);
            }
        }
        else {
            redirectAttributes.addFlashAttribute("staff_not_found", true);
        }
        return "redirect:/api/staff/list";
    }



}
