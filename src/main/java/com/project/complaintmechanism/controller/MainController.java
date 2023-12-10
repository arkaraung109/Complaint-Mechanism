package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.model.ForgotPasswordModel;
import com.project.complaintmechanism.model.UserModel;
import com.project.complaintmechanism.service.CompanyService;
import com.project.complaintmechanism.service.ComplaintService;
import com.project.complaintmechanism.service.ComplaintTitleService;
import com.project.complaintmechanism.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComplaintTitleService complaintTitleService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;

    @GetMapping({"", "/", "/dashboard"})
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String navigateToDashboardPage(Model model) {
        int companyCount = companyService.findCount();
        int complaintTitleCount = complaintTitleService.findCount();
        int allComplaintCount = complaintService.findCountByAll();
        int readComplaintCount = complaintService.findCountByReadStatus(true);
        int unreadComplaintCount = complaintService.findCountByReadStatus(false);
        int acceptedComplaintCount = complaintService.findCountByAcceptedStatus(1);
        int rejectedComplaintCount = complaintService.findCountByAcceptedStatus(0);
        int pendingComplaintCount = complaintService.findCountByAcceptedStatus(-1);
        int solvedComplaintCount = complaintService.findCountBySolvedStatus(true);
        int unsolvedComplaintCount = complaintService.findCountBySolvedStatus(false);
        model.addAttribute("companyCount", companyCount);
        model.addAttribute("complaintTitleCount", complaintTitleCount);
        model.addAttribute("allComplaintCount", allComplaintCount);
        model.addAttribute("readComplaintCount", readComplaintCount);
        model.addAttribute("unreadComplaintCount", unreadComplaintCount);
        model.addAttribute("acceptedComplaintCount", acceptedComplaintCount);
        model.addAttribute("rejectedComplaintCount", rejectedComplaintCount);
        model.addAttribute("pendingComplaintCount", pendingComplaintCount);
        model.addAttribute("solvedComplaintCount", solvedComplaintCount);
        model.addAttribute("unsolvedComplaintCount", unsolvedComplaintCount);
        return "dashboard";
    }

    @GetMapping("/login")
    public String navigateToLoginPage(Model model) {
        model.addAttribute("user", new UserModel());
        return "login";
    }

    @GetMapping("/403")
    public String navigateTo403Page() {
        return "403";
    }

    @GetMapping("/error")
    public String navigateToErrorPage() {
        return "error";
    }

    @GetMapping("/verify")
    public String verify(@Param("token") String token, Model model) {
        boolean isVerified = userService.verify(token);
        if(isVerified) {
            return "redirect:/login?verified_success";
        } else {
            return "redirect:/login?verified_failure";
        }
    }

    @GetMapping("/forgotPassword")
    public String navigateToForgotPasswordPage(Model model) {
        model.addAttribute("user", new UserModel());
        return "forgot_password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid @ModelAttribute("user") ForgotPasswordModel forgotPasswordModel, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        if (!result.hasErrors()) {
            User user = userService.findByUsername(forgotPasswordModel.getUsername());

            if(!Objects.isNull(user)) {
                String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                Random rnd = new Random();

                StringBuilder sb = new StringBuilder(8);
                for (int i = 0; i < 8; i++) {
                    sb.append(chars.charAt(rnd.nextInt(chars.length())));
                }
                String generatedPassword =  sb.toString();
                User u = userService.resetPassword(user, generatedPassword);

                StringBuffer requestURL = request.getRequestURL();
                String servletPath = request.getServletPath();
                String url = requestURL.substring(0, requestURL.length() - servletPath.length()).toString();
                userService.sendPasswordResetEmail(u, generatedPassword, url);
                return "redirect:/login?forgot_password_success";
            } else {
                redirectAttributes.addFlashAttribute("wrong_username", true);
                return "redirect:/forgetPassword";
            }
        }
        model.addAttribute("user", forgotPasswordModel);
        return "forgot_password";
    }

}
