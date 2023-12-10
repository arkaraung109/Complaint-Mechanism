package com.project.complaintmechanism.controller;

import com.project.complaintmechanism.entity.*;
import com.project.complaintmechanism.model.ComplaintDetailsModel;
import com.project.complaintmechanism.model.ComplaintModel;
import com.project.complaintmechanism.model.DailyLimitModel;
import com.project.complaintmechanism.service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@ControllerAdvice
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    private CityService cityService;
    @Autowired
    private TownshipService townshipService;
    @Autowired
    private IndustrialZoneService industrialZoneService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComplaintTitleService complaintTitleService;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private DailyLimitService dailyLimitService;

    @GetMapping("/list/{status}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String navigateToListPage(@PathVariable("status") String status, @RequestParam(defaultValue = "") String complaintTitleName, @RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, Model model, HttpSession httpSession) {
        if(Objects.equals(status, "all")) {
            httpSession.setAttribute("complaintStatus", "All");
        } else if(Objects.equals(status, "trash")) {
            httpSession.setAttribute("complaintStatus", "Trash");
        } else if(Objects.equals(status, "read")) {
            httpSession.setAttribute("complaintStatus", "Read");
        } else if(Objects.equals(status, "unread")) {
            httpSession.setAttribute("complaintStatus", "Unread");
        } else if(Objects.equals(status, "accepted")) {
            httpSession.setAttribute("complaintStatus", "Accepted");
        } else if(Objects.equals(status, "rejected")) {
            httpSession.setAttribute("complaintStatus", "Rejected");
        } else if(Objects.equals(status, "pending")) {
            httpSession.setAttribute("complaintStatus", "Pending");
        } else if(Objects.equals(status, "solved")) {
            httpSession.setAttribute("complaintStatus", "Solved");
        } else if(Objects.equals(status, "unsolved")) {
            httpSession.setAttribute("complaintStatus", "Unsolved");
        } else {
            return "redirect:/api/complaint/list/all";
        }

        httpSession.setAttribute("complaintTitleName", complaintTitleName);
        httpSession.setAttribute("date", date);
        httpSession.setAttribute("keyword", keyword);
        httpSession.setAttribute("pageNum", pageNum);
        httpSession.setAttribute("pageSize", pageSize);

        boolean tempDeletedStatus = Objects.equals(status, "all") || Objects.equals(status, "trash");
        boolean readStatus = Objects.equals(status, "read") || Objects.equals(status, "unread");
        boolean acceptedStatus = Objects.equals(status, "accepted") || Objects.equals(status, "rejected") || Objects.equals(status, "pending");
        boolean solvedStatus = Objects.equals(status, "solved") || Objects.equals(status, "unsolved");

        DailyLimit dailyLimit = dailyLimitService.findFirstByOrderById();
        DailyLimitModel dailyLimitModel = DailyLimitModel.builder()
                .id(dailyLimit.getId())
                .maxLimit(dailyLimit.getMaxLimit())
                .build();

        Page<Complaint> complaintPage = null;
        if(tempDeletedStatus) {
            complaintPage = complaintService.findByPageForTempDeletedStatus(status, complaintTitleName, date, keyword, pageNum, pageSize);
        } else if(readStatus) {
            complaintPage = complaintService.findByPageForReadStatus(status, complaintTitleName, date, keyword, pageNum, pageSize);
        } else if(acceptedStatus) {
            complaintPage = complaintService.findByPageForAcceptedStatus(status, complaintTitleName, date, keyword, pageNum, pageSize);
        } else if(solvedStatus) {
            complaintPage = complaintService.findByPageForSolvedStatus(status, complaintTitleName, date, keyword, pageNum, pageSize);
        }

        List<Complaint> complaintList = complaintPage != null ? complaintPage.getContent() : new ArrayList<>();
        int currentPage = (complaintPage != null ? complaintPage.getNumber() : 0) + 1;
        long totalItems = complaintPage != null ? complaintPage.getTotalElements() : 0;
        int totalPages = complaintPage != null ? complaintPage.getTotalPages() : 0;
        List<String> complaintTitleNameList = complaintTitleService.findAllNames();

        model.addAttribute("complaintTitleNameList", complaintTitleNameList);
        model.addAttribute("complaintList", complaintList);
        model.addAttribute("dailyLimit", dailyLimitModel);
        model.addAttribute("complaintCount", complaintService.findComplaintCountToday());
        model.addAttribute("complaintTitleName", complaintTitleName);
        model.addAttribute("date", date);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);
        return "complaint/complaint_list";
    }

    @GetMapping("/add")
    public String navigateToAddPage(Model model, RedirectAttributes redirectAttributes) {
        int dailyLimit = dailyLimitService.findFirstByOrderById().getMaxLimit();
        boolean exceed = complaintService.checkLimitExceeded(dailyLimit);

        if(exceed) {
            redirectAttributes.addFlashAttribute("exceeded", true);
            return "redirect:/api/complaint/message";
        }

        List<City> cityList = cityService.findAll();
        List<ComplaintTitle> complaintTitleList = complaintTitleService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("complaintTitleList", complaintTitleList);
        model.addAttribute("complaint", new ComplaintModel());
        return "complaint/complaint_add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("complaint") ComplaintModel complaintModel, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (!result.hasErrors()) {
            int dailyLimit = dailyLimitService.findFirstByOrderById().getMaxLimit();
            boolean exceed = complaintService.checkLimitExceeded(dailyLimit);

            if(!exceed) {
                complaintService.save(complaintModel);
                redirectAttributes.addFlashAttribute("added_success", true);
            }
            else {
                redirectAttributes.addFlashAttribute("exceeded", true);
            }
            return "redirect:/api/complaint/message";
        }

        List<City> cityList = cityService.findAll();
        List<Township> townshipList = townshipService.findByCityId(complaintModel.getCityId());
        List<IndustrialZone> industrialZoneList = industrialZoneService.findByTownshipId(complaintModel.getTownshipId());
        List<Company> companyList = companyService.findByIndustrialZoneId(complaintModel.getIndustrialZoneId());
        List<ComplaintTitle> complaintTitleList = complaintTitleService.findAll();
        model.addAttribute("cityList", cityList);
        model.addAttribute("townshipList", townshipList);
        model.addAttribute("industrialZoneList", industrialZoneList);
        model.addAttribute("companyList", companyList);
        model.addAttribute("complaintTitleList", complaintTitleList);
        model.addAttribute("complaint", complaintModel);
        return "complaint/complaint_add";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String update(@Valid @ModelAttribute("complaintDetails") ComplaintDetailsModel complaintDetailsModel, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) throws IOException {
        if(httpSession.getAttribute("complaintStatus") == null) {
            httpSession.setAttribute("complaintStatus", "All");
        }

        String complaintStatus = (String) httpSession.getAttribute("complaintStatus");
        String complaintTitleName = (String) httpSession.getAttribute("complaintTitleName");
        String date = (String) httpSession.getAttribute("date");
        String keyword = (String) httpSession.getAttribute("keyword");
        int pageNum = (Integer) httpSession.getAttribute("pageNum");
        int pageSize = (Integer) httpSession.getAttribute("pageSize");

        if (!result.hasErrors()) {
            complaintService.update(complaintDetailsModel);
            redirectAttributes.addFlashAttribute("saved_changes_success", true);

            return "redirect:/api/complaint/list/" + complaintStatus.toLowerCase() + "?complaintTitleName=" + complaintTitleName + "&date=" + date + "&keyword=" + keyword + "&pageNum=" + pageNum + "&pageSize=" + pageSize;
        }

        Complaint complaint = complaintService.findById(complaintDetailsModel.getId());
        if(!Objects.isNull(complaint)) {
            List<ComplaintTitle> complaintTitleList = complaintTitleService.findAll();
            model.addAttribute("complaintTitleList", complaintTitleList);
            model.addAttribute("complaint", complaint);
            model.addAttribute("complaintDetails", complaintDetailsModel);
            return "complaint/complaint_details";
        } else {
            redirectAttributes.addFlashAttribute("complaint_not_found", true);
            return "redirect:/api/complaint/list/" + complaintStatus.toLowerCase() + "?complaintTitleName=" + complaintTitleName + "&date=" + date + "&keyword=" + keyword + "&pageNum=" + pageNum + "&pageSize=" + pageSize;
        }
    }

    @GetMapping("/message")
    public String navigateToMessagePage() {
        return "message";
    }

    @PostMapping("/changeReadStatus")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @ResponseBody
    public void changeReadStatus(@RequestParam("idList[]") long[] idList, @RequestParam("status") boolean status) {
        for(long id : idList) {
            complaintService.changeReadStatus(id, status);
        }
    }

    @PostMapping("/changeTempDeletedStatus")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @ResponseBody
    public void changeTempDeletedStatus(@RequestParam("idList[]") long[] idList, @RequestParam("status") boolean status) {
        for(long id: idList) {
            complaintService.changeTempDeletedStatus(id, status);
        }
    }

    @PostMapping("/emptyTrash")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @ResponseBody
    public void emptyTrash() {
        complaintService.emptyTrash();
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public String navigateToDetailPage(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        if(httpSession.getAttribute("complaintStatus") == null) {
            httpSession.setAttribute("complaintStatus", "All");
        }

        Complaint complaint = complaintService.findById(id);
        if(!Objects.isNull(complaint)) {
            ComplaintDetailsModel complaintDetailsModel = ComplaintDetailsModel.builder()
                    .id(complaint.getId())
                    .remark(complaint.getRemark())
                    .acceptedStatus(complaint.getAcceptedStatus())
                    .solvedStatus(complaint.isSolvedStatus())
                    .complaintTitleSet(complaint.getComplaintTitleSet())
                    .build();
            List<ComplaintTitle> complaintTitleList = complaintTitleService.findAll();
            model.addAttribute("complaintTitleList", complaintTitleList);
            model.addAttribute("complaint", complaint);
            model.addAttribute("complaintDetails", complaintDetailsModel);
            return "complaint/complaint_details";
        } else {
            redirectAttributes.addFlashAttribute("complaint_not_found", true);

            String complaintStatus = (String) httpSession.getAttribute("complaintStatus");
            String complaintTitleName = (String) httpSession.getAttribute("complaintTitleName");
            String date = (String) httpSession.getAttribute("date");
            String keyword = (String) httpSession.getAttribute("keyword");
            int pageNum = (Integer) httpSession.getAttribute("pageNum");
            int pageSize = (Integer) httpSession.getAttribute("pageSize");
            return "redirect:/api/complaint/list/" + complaintStatus.toLowerCase() + "?complaintTitleName=" + complaintTitleName + "&date=" + date + "&keyword=" + keyword + "&pageNum=" + pageNum + "&pageSize=" + pageSize;
        }
    }

    @RequestMapping("/display/{image}/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    public void displayIdCardFront(@PathVariable("image") String image, @PathVariable("id")int id, HttpServletResponse response) throws IOException {
        Complaint complaint = complaintService.findById(id);
        response.setContentType("image/jpeg");
        byte[] bytes = switch (image) {
            case "idCardFront" -> complaint.getIdCardFront();
            case "idCardBack" -> complaint.getIdCardBack();
            case "ecPhoto1" -> complaint.getEcPhoto1();
            case "ecPhoto2" -> complaint.getEcPhoto2();
            default -> new byte[0];
        };

        InputStream inputStream = new ByteArrayInputStream(bytes);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

}
