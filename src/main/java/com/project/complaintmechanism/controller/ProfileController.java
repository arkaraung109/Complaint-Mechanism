package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.model.ChangePasswordModel;
import com.project.complaintmechanism.model.ProfileModel;
import com.project.complaintmechanism.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String navigateToProfilePage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        ProfileModel profileModel = ProfileModel.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
        model.addAttribute("profileModel", profileModel);
        return "profile/profile_edit";
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String update(@Valid @ModelAttribute("profileModel") ProfileModel profileModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            userService.update(profileModel);
            redirectAttributes.addFlashAttribute("updated_success", true);
            return "redirect:/dashboard";
        }
        model.addAttribute("profileModel", profileModel);
        return "profile/profile_edit";
    }

    @PostMapping("/checkPassword")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @ResponseBody
    public boolean checkPassword(@RequestParam("password") String password, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    @GetMapping("/changePassword")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String navigateToChangePasswordPage(Model model) {
        model.addAttribute("changePasswordModel", new ChangePasswordModel());
        return "profile/change_password";
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String changePassword(@Valid @ModelAttribute("changePasswordModel") ChangePasswordModel changePasswordModel, BindingResult result, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        if (!result.hasErrors()) {
            User user = userService.findByUsername(principal.getName());
            userService.changePassword(user, changePasswordModel.getNewPassword());
            redirectAttributes.addFlashAttribute("change_password_success", true);
            return "redirect:/dashboard";
        }
        model.addAttribute("changePasswordModel", changePasswordModel);
        return "profile/change_password";
    }

}
